package client;

import com.google.gson.Gson;

import java.util.Map;

public class ResponseException extends RuntimeException {
    private final int status;

    public ResponseException(String message, int status) {
        super(message);
        this.status = status;
    }

    public static Exception fromJson(String body, int status) {
        Map<String, String> messageMap = new Gson().fromJson(body, Map.class);
        return new ResponseException(messageMap.get("message"), status);
    }
}
