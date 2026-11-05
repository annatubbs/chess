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
            "register", "Register as a new user: \"register\" <USERNAME> <PASSWORD> <EMAIL>");

    public ChessClient() {
//        server = new ServerFacade();
        userState = UserState.LOGGED_OUT;
        stopProg = false;
    }

    public void run() {
        System.out.println("\n" + WHITE_KING + "Welcome to Chess:" + WHITE_QUEEN + "\n");
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
            case "quit" -> quit();
            default -> help();
        };
    }

    private String login(String[] params) {
        if (params.length != 2) {
            return "Incorrect format\n " + inputFormatStrs.get("login");
        }
        // if success
        userState = LOGGED_IN;
        return "Welcome " + params[0] + "!";
    }

    private String register(String[] params) {
        if (params.length != 3) {
            return "Incorrect format\n " + inputFormatStrs.get("register");
        }
        // if success
        userState = LOGGED_IN;
        return "Welcome " + params[0] + "!";
    }

    private String quit() {
        stopProg = true;
        return "Have a great day! Come back soon.";
    }

    private String help() {
        String msg = "\nOptions:\n";
        if (userState == UserState.LOGGED_OUT) {
            msg += inputFormatStrs.get("login") + "\n" + inputFormatStrs.get("register") + "\n";
        }
        msg += "Exit: \"quit\" \nHelp: \"help\"";

        return msg;
    }
}
