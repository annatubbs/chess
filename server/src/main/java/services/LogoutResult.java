package services;

import server.AuthTokens;

public class LogoutResult {
    private final String username;

    public LogoutResult(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
