package it.tabacchi.exception;

public class ExpiredRefreshTokenException extends RuntimeException {

    public ExpiredRefreshTokenException(String message) {
        super(message);
    }
}