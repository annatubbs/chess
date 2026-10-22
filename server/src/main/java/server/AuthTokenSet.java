package server;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;


public class AuthTokenSet {
    public static class AuthToken {
        private final String tokenStr;

        public AuthToken() {
            this.tokenStr = UUID.randomUUID().toString();
        }

        public AuthToken(String tokenStr) {
            this.tokenStr = tokenStr;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AuthToken authToken = (AuthToken) o;
            return Objects.equals(tokenStr, authToken.tokenStr);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(tokenStr);
        }
    }

    private final HashSet<AuthToken> authTokenSet; // developer notes: make new instance when clear. immutable

    public AuthTokenSet() {
        authTokenSet = new HashSet<>();
    }

    public AuthToken makeToken() {
        AuthToken token = new AuthToken();

        while (authTokenSet.contains(token)) { // make sure token is unique
            token = new AuthToken();
        }

        authTokenSet.add(token);
        return token;
    }

    public boolean isValidToken(AuthToken token) {
        return authTokenSet.contains(token);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthTokenSet that = (AuthTokenSet) o;
        return Objects.equals(authTokenSet, that.authTokenSet);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authTokenSet);
    }
}
