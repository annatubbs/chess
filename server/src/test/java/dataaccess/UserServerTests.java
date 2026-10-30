package dataaccess;

import dataobject.LoginResult;
import dataobject.LoginRequest;
import dataobject.RegisterRequest;
import handlers.UserHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import services.*;

public class UserServerTests extends service.UserServerTests {
    static UserHandler userHandler;

    @BeforeEach
    public void setUpUserTools() {
        userHandler = new UserHandler(userService);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void registerSuccessTest(Path path) {
        super.registerSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void registerBadRequestTest(Path path) {
        super.registerBadRequestTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void loginSuccessTest(Path path) {
        super.loginSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void loginUnauthorizedTest(Path path) {
        super.loginUnauthorizedTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void logoutSuccessTest(Path path) {
        super.logoutSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void logoutUnauthorizedTest(Path path) {
        super.logoutUnauthorizedTest(path);
    }
}
