package services;

import dataaccess.DataAccess;

public class Service {
    private final UserService userService;
    private final GameService gameService;

    public Service(DataAccess dataAccess) {
        userService = new UserService(dataAccess);
        gameService = new GameService(dataAccess);
    }
}
