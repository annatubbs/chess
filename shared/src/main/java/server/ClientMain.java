package server;


public class ClientMain {
    public static void main(String[] args) {
        Server server = new Server();
        int port = server.run(0);

        ChessClient client = new ChessClient("http://localhost:" + port);
        client.run();
    }
}
