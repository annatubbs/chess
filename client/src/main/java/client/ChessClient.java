package client;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static client.UserState.*;
import static ui.EscapeSequences.*;

public class ChessClient {
    //    private final ServerFacade server;
    private UserState userState;
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

    public ChessClient() {
//        server = new ServerFacade();
        userState = UserState.LOGGED_OUT;
        stopProg = false;
    }

    public void run() {
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
    }

    private String eval(String input) {
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

    private String login(String[] params) {
        if (userState == LOGGED_IN) {
            return "Already logged in.";
        }
        if (params.length != 2) {
            return "Incorrect format\n " + inputFormatStrs.get("login");
        }
        userState = LOGGED_IN;
        return "Welcome " + params[0] + "!";
    }

    private String register(String[] params) {
        if (userState == LOGGED_IN) {
            return "Already logged in.";
        }
        if (params.length != 3) {
            return "Incorrect format\n " + inputFormatStrs.get("register");
        }
        // if success
        userState = LOGGED_IN;
        return "Welcome " + params[0] + "!";
    }

    private String logout() {
        if (userState == LOGGED_OUT) {
            return "Not logged in.";
        }
        userState = LOGGED_OUT;
        return "Logged out [username].";
    }

    private String create(String[] params) {
        if (userState == LOGGED_OUT) {
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
        if (userState == LOGGED_OUT) {
            return "Please log in to play games.";
        }
        if (params.length != 2) {
            return "Incorrect format \n " + inputFormatStrs.get("play");
        }
        return "Playing game [gameID] [gameName] as [playerColor].";
    }

    private String observe(String[] params) {
        if (userState == LOGGED_OUT) {
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
        if (userState == UserState.LOGGED_OUT) {
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

    private String renderBoard();

}
