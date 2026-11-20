package client;

import chess.ChessGame.TeamColor;
import client.websocket.WebSocketFacade;

import java.util.*;

import static ui.EscapeSequences.*;

public class ChessClient {
    private final ServerFacade server;
    private final WebSocketFacade webSocketFacade;
    private ClientInfo clientInfo;
    private ClientGameInfo gameInfo;
    private boolean stopProg;
    private final Map<String, String> inputFormatStrs = Map.ofEntries(
            Map.entry("login", "Login as an existing user: \"login\" <USERNAME> <PASSWORD>"),
            Map.entry("register", "Register as a new user: \"register\" <USERNAME> <PASSWORD> <EMAIL>"),
            Map.entry("logout", "Logout: \"logout\""),
            Map.entry("create", "Create game: \"create\" <GAMENAME>"),
            Map.entry("list", "List all games: \"list\""),
            Map.entry("play", "Play game: \"play\" <PLAYERCOLOR:black/white> <GAMEID> "),
            Map.entry("observe", "Observe a game: \"observe\" <GAMEID>"),
            Map.entry("board", "Render chess board: \"board\""),
            Map.entry("options", "See the valid move options for a piece: \"options\" <PIECESQUARE>"),
            Map.entry("move", "Make a move: \"move\" <STARTSQUARE> <ENDSQUARE>"),
            Map.entry("leave", "Leave current game: \"leave\""),
            Map.entry("quit", "Quit: \"quit\""),
            Map.entry("help", "Help: \"help\""));

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        webSocketFacade = new WebSocketFacade(serverUrl, this);

        clientInfo = new ClientInfo();
        gameInfo = new ClientGameInfo();
        stopProg = false;
    }

    private String printPrompt() {
        String prompt = "\n";
        prompt += switch (clientInfo.getState()) {
            case LOGGED_OUT -> "";
            case LOGGED_IN -> "[LOGGED_IN]";
            case PLAYING -> "[PLAYING-" + gameInfo.getGameName() + "]";
            case OBSERVING -> "[OBSERVING-" + gameInfo.getGameName() + "]";
        };
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
            System.out.println("Server Error - quitting program..");
            stopProg = true;
        }
    }

    private String eval(String input) throws Exception {
        String[] tokens = input.split(" ");
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
            case "board" -> redrawBoard();
            case "options" -> validMoveOptions(params);
            case "move" -> makeMove(params);
            case "leave" -> leave();
            case "quit" -> quit();
            case "clear" -> clear();
            default -> help();
        };
    }

    private String register(String[] params) throws Exception { // unauthorized create game***
        if (clientInfo.getState() != ClientState.LOGGED_OUT) {
            return "Already logged in.";
        }
        if (params.length != 3) {
            return "Incorrect format\n " + inputFormatStrs.get("register");
        }

        try {
            Map<String, String> result = server.register(params);
            clientInfo = new ClientInfo(result.get("username"), result.get("authToken"), ClientState.LOGGED_IN);

            return "Welcome " + clientInfo.getUsername() + "!";
        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    private String login(String[] params) throws Exception {
        if (clientInfo.getState() != ClientState.LOGGED_OUT) {
            return "Already logged in.";
        }

        if (params.length != 2) {
            return "Incorrect format\n " + inputFormatStrs.get("login");
        }

        try {
            Map<String, String> result = server.login(params);
            clientInfo = new ClientInfo(result.get("username"), result.get("authToken"), ClientState.LOGGED_IN);
            return "Welcome " + clientInfo.getUsername() + "!";

        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    private String logout() throws Exception {
        if (clientInfo.getState() == ClientState.LOGGED_OUT) {
            return "Not logged in.";
        }

        try {
            Map<String, String> result = server.logout(clientInfo.getAuthToken());
            clientInfo = new ClientInfo();
            return "Logged out " + result.get("username") + ".";
        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    private String create(String[] params) throws Exception {
        if (clientInfo.getState() == ClientState.LOGGED_OUT) {
            return "Please log in to create games.";
        }
        if (clientInfo.getState() != ClientState.LOGGED_IN) {
            return "Please leave current name to create another";
        }
        if (params.length != 1) {
            return "Incorrect format \n " + inputFormatStrs.get("create");
        }

        try {
            Map<String, String> result = server.create(clientInfo.getAuthToken(), params);
            return "Created " + formatGameDataStr(params[0], "", "");
        } catch (ResponseException e) {
            return e.getMessage();
        }

    }

    private String list() throws Exception {
        if (clientInfo.getState() == ClientState.LOGGED_OUT) {
            return "Please log in to view games.";
        }

        try {
            ArrayList<Map<String, Object>> gameList = getGameList();
            return "Games:\n" + gameListToString(gameList);
        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    private String play(String[] params) throws Exception {
        if (clientInfo.getState() == ClientState.LOGGED_OUT) {
            return "Please log in to play games.";
        }
        if (params.length != 2) {
            return "Incorrect format \n " + inputFormatStrs.get("play");
        }
        try {
            TeamColor playerColor = TeamColor.fromString(params[0]);
            String gameIndex = params[1]; // use index to get game's data
            Map<String, Object> gameDataMap = getGameByIndex(gameIndex);

            if (!alreadyPlayer(gameDataMap, playerColor)) {
                String gameID = gameDataMap.get("gameID").toString();
                params[1] = gameID;
                Map<String, Object> result = server.play(clientInfo.getAuthToken(), params);

                gameDataMap = getGameByIndex(gameIndex); // get most recent game data
            }
            clientInfo.setState(ClientState.PLAYING);
            gameInfo = new ClientGameInfo(((Double) gameDataMap.get("gameID")).intValue(),
                    gameDataMap.get("gameName").toString(), ClientGameInfo.Role.PLAYER,
                    TeamColor.fromString(params[0]));

            String msg = "Playing " + formatGameDataStr(gameDataMap.get("gameName").toString(),
                    switchNullToEmptyObj(gameDataMap.get("whiteUsername")),
                    switchNullToEmptyObj(gameDataMap.get("blackUsername"))) + "\n";
            return msg.replaceFirst("\\R$", "") + "\n" + redrawBoard();

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String observe(String[] params) {
        if (clientInfo.getState() == ClientState.LOGGED_OUT) {
            return "Please log in to observe games.";
        }
        if (clientInfo.getState() != ClientState.LOGGED_IN) {
            return "Please leave current game to observe a different game.";
        }
        if (params.length != 1) {
            return "Incorrect format \n " + inputFormatStrs.get("observe");
        }
        try {
            String gameIndex = params[0]; // use index to get game's data
            Map<String, Object> gameDataMap = getGameByIndex(gameIndex);
            int gameID = (int) Double.parseDouble(gameDataMap.get("gameID").toString());
            String gameName = gameDataMap.get("gameName").toString();

            clientInfo.setState(ClientState.OBSERVING);
            gameInfo = new ClientGameInfo(gameID, gameName, ClientGameInfo.Role.OBSERVER);

            return "Observing:\n" + redrawBoard();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String redrawBoard() throws Exception {
        if (clientInfo.getState() == ClientState.LOGGED_OUT) {
            return "Please log in to view chess boards.";
        } else if (clientInfo.getState() == ClientState.LOGGED_IN) {
            return "Please join a game to view the board.";
        }
        String boardStr = server.getGameBoard(clientInfo.getAuthToken(), String.valueOf(gameInfo.getGameID()),
                gameInfo.getPlayerColor());

        return "Game: " + gameInfo.getGameName() + "\n" + boardStr;
    }

    private int[] getPieceIndex(String positionName) {
        positionName = positionName.toLowerCase(Locale.ROOT);
        String regex = "^[a-h][1-8]$";
        if (!positionName.matches(regex)) {
            return null;
        }

        char[] posChars = positionName.toCharArray();
        int rowIndex = posChars[1] - '0';
        int colIndex = "abcdefgh".indexOf(posChars[0]) + 1;

        return new int[]{rowIndex, colIndex};
    }

    public String makeMove(String[] params) {
        if (clientInfo.getState() != ClientState.PLAYING) {
            return "You are not in a game";
        }
        if (params.length != 2) {
            return "Incorrect format \n " + inputFormatStrs.get("move");
        }

        // check that move valid. Already sure that in right game. Check that is their turn.

        return "Move";
    }

    public String validMoveOptions(String[] params) throws Exception {
        if (clientInfo.getState() != ClientState.PLAYING) {
            return "You are not in a game";
        }
        if (params.length != 1) {
            return "Incorrect format \n " + inputFormatStrs.get("options");
        }

        int[] pieceIndex = getPieceIndex(params[0]);
        String highlightBoardStr = server.getValidMoves(clientInfo.getAuthToken(), String.valueOf(gameInfo.getGameID()),
                gameInfo.getPlayerColor(), pieceIndex);


        return "Options:\n" + highlightBoardStr;
    }

    public String leave() {
        if (clientInfo.getState() == ClientState.LOGGED_OUT) {
            return "Not logged in.";
        } else if (clientInfo.getState() == ClientState.LOGGED_IN) {
            return "You are not in a game.";
        }
        clientInfo.setState(ClientState.LOGGED_IN);
        gameInfo = new ClientGameInfo();

        return "Leaving game...";
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
        if (clientInfo.getState() == ClientState.LOGGED_OUT) {
            for (String option : new String[]{"login", "register", "list", "quit", "help"}) {
                msg += inputFormatStrs.get(option) + "\n";
            }
        } else if (clientInfo.getState() == ClientState.LOGGED_IN) {
            for (String option : new String[]{"logout", "create", "list", "play", "quit", "help"}) {
                msg += inputFormatStrs.get(option) + "\n";
            }
        } else if (clientInfo.getState() == ClientState.PLAYING) {
            for (String option : new String[]{"board", "options", "move", "leave", "quit", "help"}) {
                msg += inputFormatStrs.get(option) + "\n";
            }
        } else { // OBSERVING
            for (String option : new String[]{"logout", "board", "leave", "quit", "help"}) {
                msg += inputFormatStrs.get(option) + "\n";
            }
        }

        return msg.replaceFirst("\\R$", "");
    }

    // Helper functions

    private ArrayList<Map<String, Object>> getGameList() throws Exception {
        return server.list(clientInfo.getAuthToken());
    }

    private String switchNullToEmptyStr(String str) {
        if (str == null || str.equals("null")) {
            return "";
        } else {
            return str;
        }
    }

    private String switchNullToEmptyObj(Object str) {
        if (str == null) {
            return "";
        } else {
            return str.toString();
        }
    }

    private String formatGameDataStr(String gameName, String whiteUserName, String blackUserName) {
        return "game: " + gameName + "\n" +
                "\tWHITE_PLAYER: " + switchNullToEmptyStr(whiteUserName) + "\n" +
                "\tBLACK_PLAYER: " + switchNullToEmptyStr(blackUserName) + "\n";
    }

    private String gameListToString(ArrayList<Map<String, Object>> gameList) {
        String gameListStr = "";
        int index = 1;
        for (Map<String, Object> gameDataMap : gameList) {
            gameListStr += String.valueOf(index) + ". " +
                    formatGameDataStr(String.valueOf(gameDataMap.get("gameName")),
                            switchNullToEmptyObj(gameDataMap.get("whiteUsername")),
                            switchNullToEmptyObj(gameDataMap.get("blackUsername")));
            index++;
        }
        return gameListStr.replaceFirst("\\R$", "");
    }

    private Map<String, Object> getGameByIndex(String gameNumberStr) throws Exception { // has gameName, w+b usernames
        try {
            ArrayList<Map<String, Object>> gameList = getGameList();
            int gameNumber = Integer.parseInt(gameNumberStr);
            return gameList.get(gameNumber - 1);
        } catch (Exception e) {
            throw new ResponseException("Please type a game number from the list of games.", 400);
        }
    }

    private boolean alreadyPlayer(Map<String, Object> gameDataMap, TeamColor playerColor) {
        String username = clientInfo.getUsername();
        return (playerColor == TeamColor.WHITE && username.equals(gameDataMap.get("whiteUsername"))) ||
                (playerColor == TeamColor.BLACK && username.equals(gameDataMap.get("blackUsername")));
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

}
