package org.mi.security.exception;

/**
 * 账号过期异常
 * miss_hotdog
 */
public class AccountExpiredException extends AuthenticationException {

	private static final long serialVersionUID = -825331897983004489L;

	public AccountExpiredException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountExpiredException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AccountExpiredException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AccountExpiredException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
