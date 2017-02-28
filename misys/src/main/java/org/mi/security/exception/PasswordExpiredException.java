package org.mi.security.exception;

import java.util.Date;

/**
 * 密码过期异常
 * miss_hotdog
 */

public class PasswordExpiredException extends AuthenticationException {
	private static final long serialVersionUID = -7592198080820012888L;
	private Date lastUpdata;
	private String expired;

	public PasswordExpiredException() {
		super("密码已过期");
	}
	
	public PasswordExpiredException(Date lastUpdata, String expired) {
		super("密码已过期");
		this.lastUpdata = lastUpdata;
		this.expired = expired;
	}

	public Date getLastUpdata() {
		return lastUpdata;
	}

	public void setLastUpdata(Date lastUpdata) {
		this.lastUpdata = lastUpdata;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}
}
