package server.websocket;

import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsMessageHandler;
import io.javalin.websocket.WsMessageContext;


public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

//    private final ConnectionManager connections = new ConnectionManager();

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        System.out.println(ctx.message());
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

}
