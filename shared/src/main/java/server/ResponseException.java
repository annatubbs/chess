package server;

import com.google.gson.Gson;

import java.util.Map;

public class ResponseException extends RuntimeException {
    public ResponseException(String message) {
        super(message);
    }

    public static Exception fromJson(String body) {
        Map<String, String> messageMap = new Gson().fromJson(body, Map.class);
        return new ResponseException(messageMap.get("message"));
    }
}
