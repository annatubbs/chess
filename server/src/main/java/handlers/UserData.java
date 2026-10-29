package handlers;

import java.util.Objects;

public class UserData {
    private final String username;
    private final String password;
    private final String email;

    public UserData(RegisterRequest registerRequest) {
        this.username = Objects.requireNonNull(registerRequest.getUsername());
        this.password = Objects.requireNonNull(registerRequest.getPassword());
        this.email = Objects.requireNonNull(registerRequest.getEmail());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
