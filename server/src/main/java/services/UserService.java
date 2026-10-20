package services;

import handlers.*;

public class UserService {


    public RegisterResult register(RegisterRequest registerRequest) {
        if (DA.getUser(registerRequest.getUsername())) {
            throw AlreadyTakenException("Username " + registerRequest.getUsername() + " already taken.");
        }

        UserData userData = new UserData(registerRequest);
        DA.createUser(userData);

        DA.createAuth()
    }

    public LoginResult login(LoginRequest loginRequest) {
    }

    public void logout(LogoutRequest logoutRequest) {
    }
}
