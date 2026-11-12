package client;

import com.google.gson.Gson;
import server.ResponseException;
import server.ServerFacade;

import java.util.*;

import static client.UserState.*;
import static java.lang.Integer.parseInt;
import static ui.EscapeSequences.*;

public class ChessClient {
    private final ServerFacade server;
    private String authToken;
    private String username;
    private boolean stopProg;
    private final Map<String, String> inputFormatStrs = Map.of(
            "login", "Login as an existing user: \"login\" <USERNAME> <PASSWORD>",
            "register", "Register as a new user: \"register\" <USERNAME> <PASSWORD> <EMAIL>",
            "logout", "Logout: \"logout\"",
            "create", "Create game: \"create\" <GAMENAME>",
            "list", "List all games: \"list\"",
            "play", "Play game: \"play\" <PLAYERCOLOR:black/white> <GAMEID> ",
            "observe", "Observe a game: \"observe\" <GAMEID>",
            "quit", "Quit: \"quit\"",
            "clear", "Clear: \"clear\"",
            "help", "Help: \"help\"");
    ;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        authToken = "";
        stopProg = false;
    }

    private String printPrompt() {
        String prompt = "\n";
        if (!authToken.isEmpty()) {
            prompt += "[LOGGED_IN]";
        }
        return prompt + ">>";
    }

    public void run() {
        try {
            System.out.println("\n" + WHITE_KING + SET_TEXT_BOLD + "Welcome to Chess:" +
                    SET_TEXT_FAINT + WHITE_QUEEN + "\n");
            System.out.println(help());

            Scanner scanner = new Scanner(System.in);
            String resultMsg;
            while (!stopProg) {
                System.out.print(printPrompt());
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
        String[] tokens = input.split(" ");
//        String[] tokens = input.toLowerCase().split(" ");
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
            case "clear" -> clear();
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

        try {
            Map<String, String> result = server.register(params);

            authToken = result.get("authToken");
            return "Welcome " + result.get("username") + "!";
        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    private String login(String[] params) throws Exception {
        if (!authToken.isEmpty()) {
            return "Already logged in.";
        }
        if (params.length != 2) {
            return "Incorrect format\n " + inputFormatStrs.get("login");
        }

        try {
            Map<String, String> result = server.login(params);

            authToken = result.get("authToken");
            username = result.get("username");
            return "Welcome " + username + "!";
        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    private String logout() throws Exception {
        if (authToken.isEmpty()) {
            return "Not logged in.";
        }

        try {
            Map<String, String> result = server.logout(authToken);
            authToken = "";
            username = "";
            return "Logged out " + result.get("username") + ".";
        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    private String create(String[] params) throws Exception {
        if (authToken.isEmpty()) {
            return "Please log in to create games.";
        }
        if (params.length != 1) {
            return "Incorrect format \n " + inputFormatStrs.get("create");
        }

        try {
            Map<String, String> result = server.create(authToken, params);
            return "Created " + formatGameDataStr(params[0], "", "");
        } catch (ResponseException e) {
            return e.getMessage();
        }

    }

    private String list() throws Exception {
        if (authToken.isEmpty()) {
            return "Please log in to view games.";
        }

        try {
            ArrayList<Map<String, Object>> gameList = getGameList();
            return "Games:\n" + gameListToString(gameList);
        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    private Map<String, Object> getGameByIndex(String gameNumberStr) throws Exception {
        try {
            ArrayList<Map<String, Object>> gameList = getGameList();
            int gameNumber = Integer.parseInt(gameNumberStr);
            return gameList.get(gameNumber - 1);
        } catch (Exception e) {
            throw new ResponseException("Please type a game number from the list of games.", 400);
        }
    }

    private String play(String[] params) throws Exception {
        if (authToken.isEmpty()) {
            return "Please log in to play games.";
        }
        if (params.length != 2) {
            return "Incorrect format \n " + inputFormatStrs.get("play");
        }
        try {
            String gameIndex = params[1]; // use index to get game's data
            Map<String, Object> gameData = getGameByIndex(gameIndex);
            String gameID = gameData.get("gameID").toString();

            params[1] = gameID;
            Map<String, String> result = server.play(authToken, params);

            gameData = getGameByIndex(gameIndex); // get most recent game data
            return "Playing " + formatGameDataStr(gameData.get("gameName").toString(),
                    gameData.get("whiteUserName").toString(),
                    gameData.get("blackUserName").toString());

        } catch (Exception e) {
            return e.getMessage();
        }
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
        return "\nHave a great day!";
    }

    private String clear() throws Exception {
        if (wantsToClear()) {
            logout();
            server.clear();
            return "Cleared database.";
        } else {
            return "Did NOT clear the database.";
        }
    }

    private String help() {
        String msg = SET_TEXT_BOLD + "Options:" + SET_TEXT_FAINT + "\n";
        if (authToken.isEmpty()) {
            for (String option : new String[]{"login", "register", "list", "quit", "clear", "help"}) {
                msg += inputFormatStrs.get(option) + "\n";
            }
        } else {
            for (String option : new String[]{"logout", "create", "list", "play", "observe", "quit", "clear", "help"}) {
                msg += inputFormatStrs.get(option) + "\n";
            }
        }

        return msg.replaceFirst("\\R$", "");
    }

    // Helper functions

    private ArrayList<Map<String, Object>> getGameList() throws Exception {
        return server.list(authToken);
    }

    private String switchNullToEmptyStr(String str) {
        if (str == null || str.equals("null")) {
            return "";
        } else {
            return str.toString();
        }
    }

    private String formatGameDataStr(String gameName, String whiteUserName, String blackUserName) {
        return "game: " + gameName + "\n" +
                "\tWHITE_PLAYER: " + switchNullToEmptyStr(whiteUserName) + "\n" +
                "\tBLACK_PLAYER: " + switchNullToEmptyStr(blackUserName);
    }

    private String gameListToString(ArrayList<Map<String, Object>> gameList) {
        String gameListStr = "";
        int index = 1;
        for (Map<String, Object> gameDataMap : gameList) {
            gameListStr += String.valueOf(index) + ". " +
                    formatGameDataStr(String.valueOf(gameDataMap.get("gameName")),
                            String.valueOf(gameDataMap.get("whiteUserName")),
                            String.valueOf(gameDataMap.get("blackUserName")));
            index++;
        }
        return gameListStr;
    }

    private boolean wantsToClear() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to clear the database? (y/n)");

        String line = scanner.nextLine();
        if (line.equalsIgnoreCase("y")) {
            return true;
        } else if (line.equalsIgnoreCase("n")) {
            return false;
        } else { // bad input
            return wantsToClear();
        }
    }

//    private String renderBoard() {
//
//    }

}
