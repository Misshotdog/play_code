package org.mi.core.common;



import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.mi.core.domain.security.SysRole;
import org.mi.core.domain.security.SysUser;

/**
 * Time: 下午7:20
 */

public class SessionUser implements HttpSessionBindingListener{

    public static final String SESSION_USER_OBJECT_KEY="session_user_obj";

    private SessionUser(){}

    public static SessionUser bulider(SysUser sysUser){
        return new SessionUser().update(sysUser);
    }

    public SessionUser update(SysUser sysUser){
        this.setUserId(sysUser.getId());
        this.setUsername(sysUser.getUsername());
        this.setRole(sysUser.getRole());
        this.setEmail(sysUser.getEmail());
        return this;
    }

	/** 用户ID*/
	private Long userId;

	/** 登录用户�? */
	private String username;
	
	
	/** 用户类型 */
	private Integer usertype;

    /** 角色 */
    private SysRole role;
	
    private String email;




	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
    public void valueBound(HttpSessionBindingEvent event) {
        if(SESSION_USER_OBJECT_KEY.equals(event.getName())
                && event.getValue() instanceof SessionUser){
            SessionUser sessionUser = (SessionUser) event.getValue();
            SessionFace.userMap.put(sessionUser.getUsername(), sessionUser);
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        if(SESSION_USER_OBJECT_KEY.equals(event.getName())
                && event.getValue() instanceof SessionUser){
            SessionUser sessionUser = (SessionUser) event.getValue();
            SessionFace.userMap.remove(sessionUser.getUsername());
        }
    }
}
