package ui;

import chess.BoardImpl;
import chess.ChessGame;
import chess.GameImpl;
import chess.MoveImpl;
import com.google.gson.Gson;
import model.GameData;
import util.ExceptionUtil;
import util.ResponseException;


import java.util.Arrays;
import java.util.List;

import static util.EscapeSequences.*;


public class ChessClient implements DisplayHandler {

    private State state = State.LOGGED_OUT;
    private String authToken;
    private GameData gameData;
    final private ServiceFacade server;


    public ChessClient() throws Exception {
        server = new ServiceFacade("http://localhost:8080");
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


    private String login(String[] params) throws ResponseException {
        if (state == State.LOGGED_OUT && params.length == 2) {
            var response = server.login(params[0], params[1]);
            authToken = response.authToken();
            state = State.LOGGED_IN;
            return "Success";
        }
        return "Failure";
    }

    private String register(String[] params) throws ResponseException {
        if (state == State.LOGGED_OUT && params.length == 3) {
            var response = server.register(params[0], params[1], params[2]);
            authToken = response.authToken();
            state = State.LOGGED_IN;
            return "Success";
        }
        return "Failure";
    }

    private String logout(String[] params) throws ResponseException {
        verifyAuth();

        if (state != State.LOGGED_OUT) {
            server.logout(authToken);
            state = State.LOGGED_OUT;
            authToken = null;
            return "Success";
        }
        return "Failure";
    }

    private String create(String[] params) throws ResponseException {
        verifyAuth();

        if (params.length == 1 && state == State.LOGGED_IN) {
            var response = server.createGame(authToken, params[0]);
            return "Success";
        }
        return "Failure";
    }

    private String list(String[] params) throws ResponseException {
        verifyAuth();
        var response = server.listGames(authToken);
        return new Gson().toJson(response.games());
    }


    private String join(String[] params) throws Exception {
        verifyAuth();
        if (state == State.LOGGED_IN) {
            if (params.length == 2 && (params[1].equalsIgnoreCase("WHITE") || params[1].equalsIgnoreCase("BLACK"))) {
                var gameID = Integer.parseInt(params[0]);
                var color = ChessGame.TeamColor.valueOf(params[1].toUpperCase());
                server.joinGame(authToken, gameID, color);
                state = (params[1].equalsIgnoreCase("WHITE") ? State.WHITE : State.BLACK);
                return "Success";
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
            var move = new MoveImpl(params[0]);
            if (isTurn() && isMoveLegal(move)) {
                return "Success";
            }
        }
        return "Failure";
    }

    private String leave(String[] params) throws Exception {
        if (isPlaying() || isObserving()) {
            state = State.LOGGED_IN;
            gameData = null;
            return "Success";
        }
        return "Failure";
    }

    private String resign(String[] params) throws Exception {
        if (isPlaying()) {
            state = State.LOGGED_IN;
            gameData = null;
            return "Success";
        }
        return "Failure";
    }

    private void printGame() {
        System.out.println("\n");
        System.out.print((gameData.game().getBoard()).toString(state == State.WHITE ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK));
        System.out.println();
        printPrompt();
    }

    public void printPrompt() {
        System.out.print(RESET_TEXT_COLOR + String.format("\n[%s] >>> ", state) + SET_TEXT_COLOR_GREEN);
    }

    public boolean isMoveLegal(MoveImpl move) {
        return (gameData.game().getBoard()).isMoveLegal(move);
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
        return (isPlaying() && state.isTurn(gameData.game().getTeamTurn()));
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

    private void verifyAuth() throws ResponseException {
        if (authToken == null) {
            throw new ResponseException(401, "Please login or register");
        }
    }

}
