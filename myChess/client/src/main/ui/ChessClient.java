package ui;

import java.util.*;

import static ui.EscapeSequences.*;


public class ChessClient {

    private State state = State.LOGGED_OUT;
    private String authToken;
    final private ServerFacade server = new ServerFacade("http://localhost:8080");

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
        return getHelp(state == State.LOGGED_OUT ? loggedOutHelp : loggedInHelp);
    }

    private String quit(String[] params) {
        System.exit(0);
        return "quit";
    }


    private String login(String[] params) throws Exception {
        if (params.length == 2) {
            var response = server.login(params[0], params[1]);
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
        var response = server.logout(authToken);
        if (response.success) {
            state = State.LOGGED_OUT;
            authToken = null;
            return "Success";
        }
        return "Failure";
    }


    private String register(String[] params) throws Exception {
        if (params.length == 3) {
            var response = server.register(params[0], params[1], params[2]);
            if (response.success) {
                authToken = response.authToken;
                state = State.LOGGED_IN;
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
                printBoard(State.WHITE);
                printBoard(State.BLACK);
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
                printBoard(State.WHITE);
                printBoard(State.BLACK);
                return "Success";
            }
        }

        return "Failure";
    }


    private void printBoard(State state) {

    }

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
            new Help("join <ID> [WHITE|BLACK]", "a game"),
            new Help("observe <ID>", "a game"),
            new Help("logout", "when you are done"),
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
