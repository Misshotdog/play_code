package org.mi.security.exception;

import java.util.Date;

/**
 * 账号锁定异常
 * miss_hotdog
 */
public class AccountLockedException extends AuthenticationException {

	private static final long serialVersionUID = -7818319355313908808L;
	
	public AccountLockedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountLockedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AccountLockedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AccountLockedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
