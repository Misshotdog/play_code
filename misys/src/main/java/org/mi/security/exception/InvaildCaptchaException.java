package org.mi.security.exception;

/**
 * 验证码异常
 * miss_hotdog
 */

public class InvaildCaptchaException extends AuthenticationException {

	/** UID */
	private static final long serialVersionUID = 2921483564393257577L;

	public InvaildCaptchaException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvaildCaptchaException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvaildCaptchaException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvaildCaptchaException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
