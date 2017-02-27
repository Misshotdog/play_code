package org.mi.security.support;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/20.
 *
 * 登陆主体对象
 */
public class SubjectInfo implements Serializable {

    /** 加密后的密码 */
    private String password;

    /** 加密盐�??*/
    private String pwdSalt;

	private int result;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwdSalt() {
        return pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt;
    }

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
