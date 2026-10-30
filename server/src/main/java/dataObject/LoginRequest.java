package dataObject;

import services.BadRequestException;


public class LoginRequest {
    private String username;
    private String password;

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
