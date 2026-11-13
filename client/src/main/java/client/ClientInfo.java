package client;

public class ClientInfo {
    String username;
    String authToken;
    ClientState state;

    public ClientInfo() {
        username = "";
        authToken = "";
        state = ClientState.LOGGED_OUT;
    }

    public ClientInfo(String username, String authToken, ClientState state) {
        this.username = username;
        this.authToken = authToken;
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public ClientState getState() {
        return state;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setState(ClientState state) {
        this.state = state;
    }
}
