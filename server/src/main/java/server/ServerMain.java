package server;

import chess.*;
import client.ChessClient;

public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server();
        int port = server.run(0);

        ChessClient client = new ChessClient("http://localhost:" + port);
        client.run();

//        System.out.println("♕ 240 Chess Server running on port " + port);
    }
}