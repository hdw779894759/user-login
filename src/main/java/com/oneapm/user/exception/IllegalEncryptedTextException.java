package com.oneapm.user.exception;

public class IllegalEncryptedTextException extends RuntimeException {
    private static final long serialVersionUID = -6742735276608177493L;

    public IllegalEncryptedTextException(String message, Throwable cause) {
        super(message, cause);
    }
}
