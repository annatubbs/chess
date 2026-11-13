package client;


public class ClientMain {
//    public static void main(String[] args) {
//        ChessClient client = new ChessClient();
//        client.run();
//    }

    public static void main(String[] args) {
        String serverUrl = "http://localhost:8080";

        try {
            new ChessClient(serverUrl).run();

        } catch (Throwable ex) {
            System.out.println("Unable to start server.");
        }
    }
}
