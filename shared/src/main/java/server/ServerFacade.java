package server;

import client.ChessClient;

public class ServerFacade {
    private ChessClient chessClient;
    private String serverUrl;

    public ServerFacade(String serverUrl) {
        this.chessClient = new ChessClient();
        this.serverUrl = serverUrl;
    }
}
