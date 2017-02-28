package org.mi.security.listener;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mi.core.common.SessionFace;
import org.mi.core.common.SessionUser;
import org.mi.core.domain.security.SysUser;
import org.mi.core.service.sys.SysUserService;
import org.mi.security.exception.AccountExpiredException;
import org.mi.security.exception.AccountLockedException;
import org.mi.security.exception.AuthenticationException;
import org.mi.security.exception.InvaildCaptchaException;
import org.mi.security.exception.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by miss_hotdog
 *
 * 登陆注销监听
 *
 */
public class LoginLogoutListener {


	 private Logger logger = LoggerFactory.getLogger(LoginLogoutListener.class);

	    @Autowired
	    private SysUserService sysUserService;

	  /*  @Autowired
	    protected ImageCaptchaService imageCaptchaService;*/
	    
	    

	    public void beforeLogin(String userName, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
	        logger.debug("用户 {} 准备登陆", userName);
	        try {
//	            boolean captchaPassed = imageCaptchaService.validateResponseForID(
//	                    request.getSession().getId(), request.getParameter("captcha"));
//	            if(captchaPassed == false){
//	                throw new InvaildCaptchaException("captcha invaild.");
//	                 //onLoginFailure(userName,new InvaildCaptchaException("captcha invaild."),request,response );
//	            }
	        	
	        } catch (Exception ex) {
	            throw new InvaildCaptchaException("captchaId format invaild.", ex);
//	            onLoginFailure(userName,new InvaildCaptchaException("captcha invaild.",ex),request,response );
	        }
	    }

		public void onLoginSuccess(String userName, HttpServletRequest request, HttpServletResponse response) {
			logger.debug("用户 {} 登陆成功", userName);
			afterRememberMe(userName, request, response);
		}

	    public void onLoginFailure(String userName, AuthenticationException e, HttpServletRequest request, HttpServletResponse response) {
	    	if(e instanceof InvaildCaptchaException){
	            request.setAttribute("loginError", "您输入的验证码不正确");
	        } else if(e.getCause().getClass().getName().equals(AccountExpiredException.class.getName())) {
	            request.setAttribute("loginError", "帐号已过期");
	        } else if(e.getCause().getClass().getName().equals(AccountLockedException.class.getName())) {
	            request.setAttribute("loginError", "帐号已锁定");
	        }else if(e.getCause().getClass().getName().equals(UnknownAccountException.class.getName())) {
	            request.setAttribute("loginError", "帐号已禁用");
	        }else {
	            request.setAttribute("loginError", "登录出错");
	        }
	        logger.debug("用户 {} 登陆失败", userName);
	    }

	    public void afterRememberMe(String userName, HttpServletRequest request, HttpServletResponse response) {
	        logger.debug("用户 {} 记住我 登陆", userName);
	        //设置 sessionUser
	        SysUser sysUser = sysUserService.findByUsername(userName);
	        SessionFace.setSessionUser(request, SessionUser.bulider(sysUser));
	        SessionFace.setAttribute(request, "CLASS_IFY", "0");//设置菜单类型
	    }

	    public void beforeLogout(String userName, HttpServletRequest request, HttpServletResponse response) {
	        logger.debug("用户 {} 注销", userName);
	        logger.debug("销毁session......"); 
	        ServletContext context=request.getSession().getServletContext(); 
	        Integer count=(Integer)context.getAttribute("count"); 
	        int co=count.intValue(); 
	        count=new Integer(co-1); 
	        context.setAttribute("count", count); 
	        logger.debug("当前用户人数："+count); 
	    }


}
