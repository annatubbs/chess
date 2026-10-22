package services;

public class BadRequestException extends RuntimeException {
    public BadRequestException(Throwable cause) {
        super("Error: bad request", cause);
    }

    public BadRequestException() {
        super("Error: bad request");
    }
}
