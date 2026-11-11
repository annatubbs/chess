package client;

import server.ServerFacade;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static client.UserState.*;
import static ui.EscapeSequences.*;

public class ChessClient {
    private final ServerFacade server;
    private String authToken;
    //    private UserState userState;
    private boolean stopProg;
    private final Map<String, String> inputFormatStrs = Map.of(
            "login", "Login as an existing user: \"login\" <USERNAME> <PASSWORD>",
            "register", "Register as a new user: \"register\" <USERNAME> <PASSWORD> <EMAIL>",
            "logout", "Logout: \"logout\"",
            "create", "Create game: \"create\" <GAMENAME>",
            "list", "List all games: \"list\"",
            "play", "Play game: \"play\" <GAMEID> <PLAYERCOLOR>",
            "observe", "Observe a game: \"observe\" <GAMEID>",
            "quit", "Quit: \"quit\"",
            "help", "Help: \"help\"");
    ;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        authToken = "";
//        userState = UserState.LOGGED_OUT;
        stopProg = false;
    }

    public void run() {
        try {
            System.out.println("\n" + WHITE_KING + SET_TEXT_BOLD + "Welcome to Chess:" +
                    SET_TEXT_FAINT + WHITE_QUEEN + "\n");
            System.out.println(help());

            Scanner scanner = new Scanner(System.in);
            String resultMsg = "";
            while (!stopProg) {
                System.out.print(">> ");
                String line = scanner.nextLine();

                resultMsg = eval(line);
                System.out.println(resultMsg);
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage()); //****
        }
    }

    private String eval(String input) throws Exception {
        String[] tokens = input.toLowerCase().split(" ");
        String cmd = (tokens.length > 0) ? tokens[0] : "help";
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);

        return switch (cmd) {
            case "login" -> login(params);
            case "register" -> register(params);
            case "logout" -> logout();
            case "create" -> create(params);
            case "list" -> list();
            case "play" -> play(params);
            case "observe" -> observe(params);
            case "quit" -> quit();
            default -> help();
        };
    }

    private String register(String[] params) throws Exception {
        if (!authToken.isEmpty()) {
            return "Already logged in.";
        }
        if (params.length != 3) {
            return "Incorrect format\n " + inputFormatStrs.get("register");
        }

        Map<String, String> result = server.register(params);
        authToken = result.get("authToken");

        return "Welcome " + result.get("username") + "!";
    }

    private String login(String[] params) throws Exception {
        if (!authToken.isEmpty()) {
            return "Already logged in.";
        }
        if (params.length != 2) {
            return "Incorrect format\n " + inputFormatStrs.get("login");
        }

        Map<String, String> result = server.login(params);
        authToken = result.get("authToken");

        return "Welcome " + result.get("username") + "!";
    }

    private String logout() {
        if (authToken.isEmpty()) {
            return "Not logged in.";
        }
//        userState = LOGGED_OUT;
        return "Logged out [username].";
    }

    private String create(String[] params) {
        if (authToken.isEmpty()) {
            return "Please log in to create games.";
        }
        if (params.length != 1) {
            return "Incorrect format \n " + inputFormatStrs.get("create");
        }

        return "Created game: " + params[0]; // list game data
    }

    private String list() {
        return "Games:\n";
    }

    private String play(String[] params) {
        if (authToken.isEmpty()) {
            return "Please log in to play games.";
        }
        if (params.length != 2) {
            return "Incorrect format \n " + inputFormatStrs.get("play");
        }
        return "Playing game [gameID] [gameName] as [playerColor].";
    }

    private String observe(String[] params) {
        if (authToken.isEmpty()) {
            return "Please log in to observe games.";
        }
        if (params.length != 1) {
            return "Incorrect format \n " + inputFormatStrs.get("observe");
        }
        return "Observing game [gameID] [gameName].";
    }

    private String quit() {
        stopProg = true;
        return "\nHave a great day! Come back soon.";
    }

    private String help() {
        String msg = SET_TEXT_BOLD + "Options:" + SET_TEXT_FAINT + "\n";
        if (authToken.isEmpty()) {
            for (String option : new String[]{"login", "register", "list", "quit", "help"}) {
                msg += inputFormatStrs.get(option) + "\n";
            }
        } else {
            for (String option : new String[]{"logout", "create", "list", "play", "quit", "help"}) {
                msg += inputFormatStrs.get(option) + "\n";
            }
        }
        msg += "Quit: \"quit\" \nHelp: \"help\"";

        return msg;
    }

//    private String renderBoard() {
//
//    }

}
