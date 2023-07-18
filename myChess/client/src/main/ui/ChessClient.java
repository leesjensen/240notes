package ui;

import chess.Board;
import chess.ChessBoard;
import chess.ChessGame;
import chess.Game;

import java.util.*;

import static util.EscapeSequences.*;


public class ChessClient {

    private State state = State.LOGGED_OUT;
    private String authToken;
    final private ServerFacade server = new ServerFacade("http://localhost:8080");

    private Game game;

    public String eval(String input) throws Exception {
        input = input.toLowerCase();
        var tokens = input.split(" ");
        if (tokens.length == 0) {
            tokens = new String[]{"Help"};
        }

        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        try {
            return (String) this.getClass().getDeclaredMethod(tokens[0], String[].class).invoke(this, new Object[]{params});
        } catch (NoSuchMethodException e) {
            throw new Exception("Unknown command");
        }
    }

    public void clear() throws Exception {
        server.clear();
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
                server.joinGame(authToken, params[0], params[1]);
                state = (params[1].equalsIgnoreCase("WHITE") ? State.WHITE : State.BLACK);
                game = new Game();
                game.getBoard().resetBoard();
                printGame(game.getBoard(), State.BLACK);
                printGame(game.getBoard(), State.WHITE);
                return "Success";
            }
        }

        return "Failure";
    }


    private String observe(String[] params) throws Exception {
        verifyAuth();
        if (state == State.LOGGED_IN) {
            if (params.length == 1) {
                server.joinGame(authToken, params[0], "");
                state = State.OBSERVING;
                game = new Game();
                game.getBoard().resetBoard();
                printGame(game.getBoard(), State.WHITE);
                printGame(game.getBoard(), State.BLACK);
                return "Success";
            }
        }

        return "Failure";
    }

    private String redraw(String[] params) {
        Board board = new Board();
        board.resetBoard();
        return board.toString(ChessGame.TeamColor.WHITE) + "\n" + board.toString(ChessGame.TeamColor.BLACK);
    }

    private String legal(String[] params) throws Exception {
        throw new NoSuchMethodException();
    }

    private String move(String[] params) throws Exception {
        throw new NoSuchMethodException();
    }

    private String leave(String[] params) throws Exception {
        throw new NoSuchMethodException();
    }

    private String resign(String[] params) throws Exception {
        throw new NoSuchMethodException();
    }

    private void printGame(ChessBoard board, State state) {
        System.out.print(((Board) board).toString(state == State.WHITE ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK));
        System.out.println();
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
