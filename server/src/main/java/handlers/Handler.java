package handlers;

import services.Service;

public class Handler {

    private final UserHandler userHandler;
    private final GameHandler gameHandler;

    public Handler(Service service) {
        userHandler = new UserHandler(service.getUserService());
        gameHandler = new GameHandler(service.getGameService());
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
