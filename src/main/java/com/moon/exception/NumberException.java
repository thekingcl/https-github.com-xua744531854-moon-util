package com.moon.exception;

/**
 * @author benshaoye
 */
public class NumberException extends RuntimeException {

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NumberException() {
    }

    public NumberException(String message) {
        super(message);
    }

    public NumberException(String message, Exception e) {
        super(message, e);
    }

    /*
     * int
     */

    public NumberException(int value) {
        this(String.format("value: %d", value));
    }

    public NumberException(int value, String message) {
        this(String.format("value: %d; message: %s", value, message));
    }

    public NumberException(int value, String message, Exception e) {
        this(String.format("value: %d; message: %s", value, message), e);
    }

    public NumberException(int value, Exception e) {
        this(String.format("value: %d", value), e);
    }

    /*
     * long
     */

    public NumberException(long value) {
        this(String.format("value: %d", value));
    }

    public NumberException(long value, String message) {
        this(String.format("value: %d; message: %s", value, message));
    }

    public NumberException(long value, String message, Exception e) {
        this(String.format("value: %d; message: %s", value, message), e);
    }

    public NumberException(long value, Exception e) {
        this(String.format("value: %d", value), e);
    }

    /*
     * double
     */

    public NumberException(double value) {
        this(String.format("value: %d", value));
    }

    public NumberException(double value, String message) {
        this(String.format("value: %d; message: %s", value, message));
    }

    public NumberException(double value, String message, Exception e) {
        this(String.format("value: %d; message: %s", value, message), e);
    }

    public NumberException(double value, Exception e) {
        this(String.format("value: %d", value), e);
    }
}
