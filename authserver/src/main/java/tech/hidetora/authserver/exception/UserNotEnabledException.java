package tech.hidetora.authserver.exception;

public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserNotEnabledException(String msg) {
        super(msg);
    }
}
