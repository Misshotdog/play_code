package org.mi.security.exception;

/**
 * miss_hotdog
 */
public class AuthenticationException extends org.apache.shiro.authc.AuthenticationException {

    public AuthenticationException() {
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
