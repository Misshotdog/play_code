package org.mi.security.exception;

/**
 * 
 * 未知账号异常
 * Created by miss_hotdog
 * 
 */
public class UnknownAccountException extends AuthenticationException {

	private static final long serialVersionUID = -4863368041612408570L;

	public UnknownAccountException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UnknownAccountException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UnknownAccountException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UnknownAccountException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
