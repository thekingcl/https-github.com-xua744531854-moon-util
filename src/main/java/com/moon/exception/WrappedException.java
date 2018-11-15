package com.moon.exception;

/**
 * @author benshaoye
 */
public class WrappedException extends RuntimeException {
    public WrappedException() {
    }

    public WrappedException(String message) {
        super(message);
    }

    public WrappedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrappedException(Throwable cause) {
        super(cause);
    }

    public WrappedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
