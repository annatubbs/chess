package services;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Error: unauthorized");
    }
}
