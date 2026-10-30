package dataObject;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class UserData {
    private final String username;
    private final String password;
    private final String email;

    public UserData(RegisterRequest registerRequest) {
        this.username = Objects.requireNonNull(registerRequest.getUsername());
        this.password = hashPassword(Objects.requireNonNull(registerRequest.getPassword()));
        this.email = Objects.requireNonNull(registerRequest.getEmail());
    }

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public UserData(String username, String password, String email) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.email = Objects.requireNonNull(email);
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

    @Override
    public String toString() {
        return "UserData{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
