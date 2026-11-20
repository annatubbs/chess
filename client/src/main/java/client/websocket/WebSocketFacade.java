package client.websocket;

import client.ChessClient;
import client.ResponseException;
import jakarta.websocket.*;

import java.net.URI;

public class WebSocketFacade extends Endpoint {
    private final Session session;

    public WebSocketFacade(String serverURL, ChessClient chessClient) {
        try {
            serverURL = serverURL.replace("http", "ws");
            URI socketURI = new URI(serverURL + "/ws");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

//            //set message handler
//            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
//                @Override
//                public void onMessage(String message) {
//                    Notification notification = new Gson().fromJson(message, Notification.class);
//                    notificationHandler.notify(notification);
//                }
//            });
        } catch (Exception e) {
            throw new ResponseException("Error: " + e.getMessage(), 500);
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
