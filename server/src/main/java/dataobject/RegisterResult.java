package dataobject;

public class RegisterResult {
    private final String username;
    private final String authToken;

    public RegisterResult(AuthData authData) {
        this.username = authData.getUsername();
        this.authToken = authData.getAuthToken();
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        return "RegisterResult{" +
                "username='" + username + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
