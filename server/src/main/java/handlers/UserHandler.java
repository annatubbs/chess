package handlers;

import com.google.gson.Gson;
import services.*;
import server.AuthTokenSet.*;

import java.util.HashMap;
import java.util.Map;

public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public HashMap<String, Object> register(String json) throws Exception {

        RegisterRequest registerRequest = new Gson().fromJson(json, RegisterRequest.class);

        RegisterResult registerResult = userService.register(registerRequest);

        return new HashMap<>(Map.of("username", registerResult.getUsername(),
                "authToken", registerResult.getAuthToken()));
    }

    public HashMap<String, Object> login(String json) throws Exception {
        LoginRequest loginRequest = new Gson().fromJson(json, LoginRequest.class);

        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new BadRequestException();
        }

        LoginResult loginResult = userService.login(loginRequest);

        return new HashMap<>(Map.of("username", loginResult.getUsername(),
                "authToken", loginResult.getAuthToken()));
    }

    public void logout(String authToken) throws Exception {
        LogoutResult logoutResult = userService.logout(authToken);
    }

}
