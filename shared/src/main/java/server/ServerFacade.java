package server;

import chess.*;
import com.google.gson.*;

import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.Map;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;


    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Map<String, String> register(String[] params) throws Exception {
        Map<String, String> body = Map.of("username", params[0], "password", params[1], "email", params[2]);
        HttpRequest request = buildRequest("POST", "/user", body);
        HttpResponse<String> response = sendRequest(request);

        return handleResponse(response, Map.class);
    }

    public Map<String, String> login(String[] params) throws Exception {
        Map<String, String> body = Map.of("username", params[0], "password", params[1]);
        HttpRequest request = buildRequest("POST", "/session", body);
        HttpResponse<String> response = sendRequest(request);

        return handleResponse(response, Map.class);
    }

    public Map<String, String> logout(String authToken) throws Exception {
        HttpRequest request = buildRequestWithHeader("DELETE", "/session", authToken, null);
        HttpResponse<String> response = sendRequest(request);
        return handleResponse(response, Map.class);
    }

    public Map<String, String> create(String authToken, String[] params) throws Exception {
        Map<String, String> body = Map.of("gameName", params[0]);
        HttpRequest request = buildRequestWithHeader("POST", "/game", authToken, body);
        HttpResponse<String> response = sendRequest(request);
        return handleResponse(response, Map.class);
    }

    public ArrayList<Map<String, Object>> list(String authToken) throws Exception {
        HttpRequest request = buildRequestWithHeader("GET", "/game", authToken, null);
        HttpResponse<String> response = sendRequest(request);
        Map<String, ArrayList> responseMap = handleResponse(response, Map.class);

        return responseMap.get("games");
    }

    public String getGameBoard(String authToken, String gameID, ChessGame.TeamColor playerColor) throws Exception {
        Map<String, String> body = Map.of("gameID", gameID);
        HttpRequest request = buildRequestWithHeader("GET", "/image", authToken, body);
        HttpResponse<String> response = sendRequest(request);
        ChessGame game = handleResponse(response, ChessGame.class);

        if (playerColor == ChessGame.TeamColor.BLACK) {
            return game.getBoard().toStringBlackPerspective();
        } else { // team color white or observing
            return game.getBoard().toString();
        }
    }

    public Map<String, String> play(String authToken, String[] params) throws Exception {
        Map<String, String> body = Map.of("playerColor", params[0], "gameID", params[1]);
        HttpRequest request = buildRequestWithHeader("PUT", "/game", authToken, body);
        HttpResponse<String> response = sendRequest(request);

        return handleResponse(response, Map.class);
    }

    public void clear() throws Exception {
        HttpRequest request = buildRequest("DELETE", "/db", null);
        HttpResponse<String> response = sendRequest(request);
        handleResponse(response, null);
    }

    // Process Requests
    private HttpRequest buildRequest(String method, String path, Object body) {
        return buildRequestWithHeader(method, path, "", body);
    }

    private HttpRequest buildRequestWithHeader(String method, String path, String authToken, Object body) {
        Builder request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-type", "application/json");
        }
        if (!authToken.isEmpty()) {
            request.header("Authorization", authToken);
        }
        return request.build();
    }

    private BodyPublisher makeRequestBody(Object body) {
        if (body != null) {
            return BodyPublishers.ofString(new Gson().toJson(body));
        } else {
            return BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws Exception {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private boolean errorStatus(int status) {
        return status / 100 != 2;
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> returnType) throws Exception {
        int status = response.statusCode();
        String body = response.body();

        if (errorStatus(status)) {
            if (body != null) {
                throw ResponseException.fromJson(body, status);
            }
        }

        if (returnType == null) {
            return null;
        }
        if (returnType == ChessGame.class) {
            return makeChessPieceGson().fromJson(body, returnType);
        }
        return new Gson().fromJson(body, returnType);
    }

    private Gson makeChessPieceGson() throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChessPiece.class, (JsonDeserializer<ChessPiece>)
                        (json, type, context) -> {
                            JsonObject object = json.getAsJsonObject();

                            String pieceType = object.get("type").getAsString();
                            return switch (pieceType) {
                                case "PAWN" -> context.deserialize(json, Pawn.class);
                                case "ROOK" -> context.deserialize(json, Rook.class);
                                case "KNIGHT" -> context.deserialize(json, Knight.class);
                                case "BISHOP" -> context.deserialize(json, Bishop.class);
                                case "KING" -> context.deserialize(json, King.class);
                                case "QUEEN" -> context.deserialize(json, Queen.class);
                                default -> throw new JsonParseException("Error in data conversion.");
                            };
                        }).create();
        return gson;
    }
}

