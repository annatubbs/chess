package handlers;

import services.BadRequestException;

public class LoginRequest {

    private final String username;
    private final String password;

    public LoginRequest(String username, String password) {
        try {
            this.username = username;
            this.password = password;
        } catch (Exception e) {
            throw new BadRequestException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
