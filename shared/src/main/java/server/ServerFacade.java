package server;

import client.ChessClient;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.*;
import java.util.Map;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.util.TreeMap;

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
                throw ResponseException.fromJson(body);
            }
        }

        if (returnType == null) {
            return null;
        }
        return new Gson().fromJson(body, returnType);
    }
}

