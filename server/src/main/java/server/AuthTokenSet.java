package server;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;


public class AuthTokenSet {

    private final HashSet<String> authTokenSet; // developer notes: make new instance when clear. immutable

    public AuthTokenSet() {
        authTokenSet = new HashSet<>();
    }

    public String makeToken() {
        String authToken = UUID.randomUUID().toString();

        while (authTokenSet.contains(authToken)) { // make sure token is unique
            authToken = UUID.randomUUID().toString();
        }

        authTokenSet.add(authToken);
        return authToken;
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
