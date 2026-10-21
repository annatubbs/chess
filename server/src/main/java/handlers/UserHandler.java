package handlers;

import chess.ChessGame;
import io.javalin.http.Context;
import services.*;
import server.AuthTokenSet.*;

import java.util.HashMap;
import java.util.Map;

public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public HashMap<String, Object> register(Context ctx) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(ctx.formParam("username"),
                ctx.formParam("password"), ctx.formParam("email"));

        RegisterResult registerResult = userService.register(registerRequest);

        return new HashMap<>(Map.of("username", registerResult.getUsername(),
                "authToken", registerResult.getAuthToken()));
    }

    public HashMap<String, Object> login(Context ctx) throws Exception {
        LoginRequest loginRequest = new LoginRequest(ctx.formParam("username"),
                ctx.formParam("password"));

        LoginResult loginResult = userService.login(loginRequest);

        return new HashMap<>(Map.of("username", loginResult.getUsername(),
                "authToken", loginResult.getAuthToken()));
    }

    public HashMap<String, Object> logout(Context ctx) throws Exception {
        AuthToken authToken = new AuthToken(ctx.formParam("authToken"));

        LogoutResult logoutResult = userService.logout(authToken);
        return new HashMap<>(Map.of("username", logoutResult.getUsername()));
    }

}
