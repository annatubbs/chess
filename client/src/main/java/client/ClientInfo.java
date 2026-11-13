package client;

public class ClientInfo {
    String username;
    String authToken;
    UserState state;

    public ClientInfo() {
        username = "";
        authToken = "";
        state = UserState.LOGGED_OUT;
    }

    public String getAuthToken() {
        return authToken;
    }

    public S
}
