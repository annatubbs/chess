package handlers;

import services.BadRequestException;

public class RegisterRequest {

    private final String username;
    private final String password;
    private final String email;

    public RegisterRequest(String username, String password, String email) {
        try {
            this.username = username;
            this.password = password;
            this.email = email;
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

    public String getEmail() {
        return email;
    }
}
