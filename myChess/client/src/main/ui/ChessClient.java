package ui;

import chess.*;
import model.GameData;
import util.ExceptionUtil;
import webSocketMessages.userCommands.GameCommand;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.MoveCommand;

import java.util.*;

import static util.EscapeSequences.*;


public class ChessClient implements DisplayHandler {

    private State state = State.LOGGED_OUT;
    private String authToken;
    private GameData gameData;
    final private SeviceFacade server;
    final private WebSocketFacade webSocket;


    public ChessClient() throws Exception {
        server = new SeviceFacade("http://localhost:8080");
        webSocket = new WebSocketFacade("ws://localhost:8080/connect", this);
    }

    public String eval(String input) throws Exception {

        var result = "Error with command. Try: Help";
        try {
            input = input.toLowerCase();
            var tokens = input.split(" ");
            if (tokens.length == 0) {
                tokens = new String[]{"Help"};
            }

            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            try {
                result = (String) this.getClass().getDeclaredMethod(tokens[0], String[].class).invoke(this, new Object[]{params});
            } catch (NoSuchMethodException e) {
                result = String.format("Unknown command\n%s", help(params));
            }
        } catch (Throwable e) {
            var root = ExceptionUtil.getRoot(e);
            result = String.format("Error: %s", root.getMessage());
        }
        System.out.print(RESET_TEXT_COLOR + result);
        return result;
    }

    public void clear() throws Exception {
        server.clear();
    }

    private String clear(String[] params) throws Exception {
        clear();
        state = State.LOGGED_OUT;
        gameData = null;
        return "Success";
    }

    private String help(String[] params) {
        return switch (state) {
            case LOGGED_IN -> getHelp(loggedInHelp);
            case OBSERVING -> getHelp(ObservingHelp);
            case BLACK, WHITE -> getHelp(playingHelp);
            default -> getHelp(loggedOutHelp);
        };
    }

    private String quit(String[] params) {
        System.exit(0);
        return "quit";
    }


    private String login(String[] params) throws Exception {
        if (state == State.LOGGED_OUT && params.length == 2) {
            var response = server.login(params[0], params[1]);
            if (response.success) {
                authToken = response.authToken;
                state = State.LOGGED_IN;
                return "Success";
            }
        }
        return "Failure";
    }

    private String register(String[] params) throws Exception {
        if (state == State.LOGGED_OUT && params.length == 3) {
            var response = server.register(params[0], params[1], params[2]);
            if (response.success) {
                authToken = response.authToken;
                state = State.LOGGED_IN;
                return "Success";
            }
        }
        return "Failure";
    }

    private String logout(String[] params) throws Exception {
        verifyAuth();

        if (state != State.LOGGED_OUT) {
            var response = server.logout(authToken);
            if (response.success) {
                state = State.LOGGED_OUT;
                authToken = null;
                return "Success";
            }
        }
        return "Failure";
    }

    private String create(String[] params) throws Exception {
        verifyAuth();

        if (params.length == 1 && state == State.LOGGED_IN) {
            var response = server.createGame(authToken, params[0]);
            if (response.success) {
                return "Success";
            }
        }
        return "Failure";
    }

    private String list(String[] params) throws Exception {
        verifyAuth();
        var response = server.listGames(authToken);
        if (response.success) {
            return response.toString();
        }
        return "Failure";
    }


    private String join(String[] params) throws Exception {
        verifyAuth();
        if (state == State.LOGGED_IN) {
            if (params.length == 2 && (params[1].equalsIgnoreCase("WHITE") || params[1].equalsIgnoreCase("BLACK"))) {
                var gameID = Integer.parseInt(params[0]);
                var color = ChessGame.TeamColor.valueOf(params[1].toUpperCase());
                var joinResponse = server.joinGame(authToken, gameID, color);
                if (joinResponse.success) {
                    state = (params[1].equalsIgnoreCase("WHITE") ? State.WHITE : State.BLACK);
                    webSocket.sendCommand(new JoinPlayerCommand(authToken, gameID, color));
                    return "Success";
                }
            }
        }

        return "Failure";
    }


    private String observe(String[] params) throws Exception {
        verifyAuth();
        if (state == State.LOGGED_IN) {
            if (params.length == 1) {
                var gameID = Integer.parseInt(params[0]);
                server.joinGame(authToken, gameID, null);
                webSocket.sendCommand(new GameCommand(GameCommand.CommandType.JOIN_OBSERVER, gameID, authToken));
                state = State.OBSERVING;
                return "Success";
            }
        }

        return "Failure";
    }

    private String redraw(String[] params) {
        if (isPlaying() || isObserving()) {
            printGame();
            return "Success";
        }
        return "Failure";
    }

    private String legal(String[] params) throws Exception {
        throw new NoSuchMethodException();
    }

    private String move(String[] params) throws Exception {
        verifyAuth();
        if (params.length == 1) {
            var move = new Move(params[0]);
            if (isTurn() && isMoveLegal(move)) {
                webSocket.sendCommand(new MoveCommand(authToken, gameData.getGameID(), move));
                return "Success";
            }
        }
        return "Failure";
    }

    private String leave(String[] params) throws Exception {
        if (isPlaying() || isObserving()) {
            webSocket.sendCommand(new GameCommand(GameCommand.CommandType.LEAVE, gameData.getGameID(), authToken));
            state = State.LOGGED_IN;
            gameData = null;
            return "Success";
        }
        return "Failure";
    }

    private String resign(String[] params) throws Exception {
        if (isPlaying()) {
            webSocket.sendCommand(new GameCommand(GameCommand.CommandType.RESIGN, gameData.getGameID(), authToken));
            state = State.LOGGED_IN;
            gameData = null;
            return "Success";
        }
        return "Failure";
    }

    private void printGame() {
        System.out.println("\n");
        System.out.print(((Board) gameData.getGame().getBoard()).toString(state == State.WHITE ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK));
        System.out.println();
        printPrompt();
    }

    public void printPrompt() {
        System.out.print(RESET_TEXT_COLOR + String.format("\n[%s] >>> ", state) + SET_TEXT_COLOR_GREEN);
    }

    public boolean isMoveLegal(Move move) {
        return ((Board) gameData.getGame().getBoard()).isMoveLegal(move);
    }

    public boolean isPlaying() {
        return (gameData != null && (state == State.WHITE || state == State.BLACK) && !isGameOver());
    }


    public boolean isObserving() {
        return (gameData != null && (state == State.OBSERVING));
    }

    public boolean isGameOver() {
        return (gameData != null && gameData.isGameOver());
    }

    public boolean isTurn() {
        return (isPlaying() && state.isTurn(gameData.getGame().getTeamTurn()));
    }

    @Override
    public void updateBoard(GameData game) {
        this.gameData = game;
        printGame();
    }

    @Override
    public void message(String message) {
        System.out.println();
        System.out.println(SET_TEXT_COLOR_MAGENTA + "NOTIFY: " + message);
        printPrompt();
    }

    @Override
    public void error(String message) {
        System.out.println(message);
    }

    /**
     * Representation of all the possible client commands.
     */
    private static record Help(String cmd, String description) {
    }

    static final List<Help> loggedOutHelp = List.of(
            new Help("register <USERNAME> <PASSWORD> <EMAIL>", "to create an account"),
            new Help("login <USERNAME> <PASSWORD>", "to play chess"),
            new Help("quit", "playing chess"),
            new Help("help", "with possible commands")
    );

    static final List<Help> loggedInHelp = List.of(
            new Help("create <NAME>", "a game"),
            new Help("list", "games"),
            new Help("join <ID> [WHITE|BLACK|<empty>]", "a game"),
            new Help("observe <ID>", "a game"),
            new Help("logout", "when you are done"),
            new Help("quit", "playing chess"),
            new Help("help", "with possible commands")
    );

    static final List<Help> ObservingHelp = List.of(
            new Help("legal", "moves for the current board"),
            new Help("redraw", "the board"),
            new Help("leave", "the game"),
            new Help("quit", "playing chess"),
            new Help("help", "with possible commands")
    );

    static final List<Help> playingHelp = List.of(
            new Help("move <rc-rc>", "a piece"),
            new Help("legal", "moves for the current board"),
            new Help("redraw", "the board"),
            new Help("leave", "the game"),
            new Help("resign", "the game without leaving it"),
            new Help("quit", "playing chess"),
            new Help("help", "with possible commands")
    );

    private String getHelp(List<Help> help) {
        StringBuilder sb = new StringBuilder();
        for (var me : help) {
            sb.append(String.format("  %s%s%s - %s%s%s%n", SET_TEXT_COLOR_BLUE, me.cmd, RESET_TEXT_COLOR, SET_TEXT_COLOR_MAGENTA, me.description, RESET_TEXT_COLOR));
        }
        return sb.toString();

    }

    private void verifyAuth() throws Exception {
        if (authToken == null) {
            throw new Exception("Please login or register");
        }
    }

}
