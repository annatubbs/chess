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
    public void registerSuccessTest(Path path) throws Exception {
        super.registerSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void registerBadRequestTest(Path path) throws Exception {
        super.registerBadRequestTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void loginSuccessTest(Path path) {
        super.loginSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void loginUnauthorizedTest(Path path) throws Exception {
        super.loginUnauthorizedTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void logoutSuccessTest(Path path) throws Exception {
        super.logoutSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void logoutUnauthorizedTest(Path path) throws Exception {
        super.logoutUnauthorizedTest(path);
    }
}
