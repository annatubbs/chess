package server;

import client.ChessClient;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.*;
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

    private HttpRequest buildRequest(String method, String path, Map<String, String> body) {
        Builder request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-type", "application/json");
        }
        return request.build();
    }

    private BodyPublisher makeRequestBody(Map<String, String> request) {
        if (request != null) {
            return BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws Exception {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }


    private <T> T handleResponse(HttpResponse<String> response, Class<T> returnType) {
        int status = response.statusCode();
        if (status / 100 == 2) { // 200 status codes. Success
            String body = response.body();
        }

        if (returnType != null) {
            return new Gson().fromJson(response.body(), returnType);
        } else {
            return null;
        }
    }
}

