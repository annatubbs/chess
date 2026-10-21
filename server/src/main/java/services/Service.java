package services;

import dataaccess.DataAccess;

public class Service {
    private final UserService userService;
    private final GameService gameService;

    public Service(DataAccess dataAccess) {
        userService = new UserService(dataAccess.getAuthDataAccess(), dataAccess.getUserDataAccess());
        gameService = new GameService(dataAccess.getAuthDataAccess(), dataAccess.getGameDataAccess());
    }

    public UserService getUserService() {
        return userService;
    }

    public GameService getGameService() {
        return gameService;
    }
}
