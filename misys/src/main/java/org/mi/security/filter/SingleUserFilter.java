package org.mi.security.filter;

import com.feinno.framework.common.web.support.JsonResult;
import com.feinno.framework.utils.mapper.JsonMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.mi.security.exception.AuthenticationException;
import org.mi.security.listener.LoginLogoutListener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 
 *
 */
public class SingleUserFilter extends AccessControlFilter  {

    private Map<String, String> sessionMap = new HashMap<String, String>();

    private String ajaxRepeatLoginCode="FEINNO_SECURITY2_REPEAT_LOGIN";

    private String ajaxRepeatLoginMsg="你的账号在其他地方，你已被迫下线";

    private String promptUrl;


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String userName = (String) super.getSubject(request, response).getPrincipal();
        String sessionId = sessionMap.get(userName);
        return sessionId==null || sessionId!=((HttpServletRequest)request).getSession().getId();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //退出登陆
        super.getSubject(request, response).logout();
        //重复登陆，提出提示
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if ((httpRequest.getHeader("x-requested-with") != null && httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
                || (httpRequest.getRequestURI()!=null && httpRequest.getRequestURI().indexOf("ajax")!=-1)) {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JsonMapper.nonDefaultMapper().toJson(new JsonResult(ajaxRepeatLoginCode, ajaxRepeatLoginMsg)));
            printWriter.flush();
            printWriter.close();
        }else{
            if (StringUtils.isNotBlank(promptUrl)) {
                WebUtils.issueRedirect(request, response, promptUrl);
            } else {
                WebUtils.issueRedirect(request, response, super.getLoginUrl());
            }
        }
        return false;
    }




    public String getAjaxRepeatLoginCode() {
        return ajaxRepeatLoginCode;
    }

    public void setAjaxRepeatLoginCode(String ajaxRepeatLoginCode) {
        this.ajaxRepeatLoginCode = ajaxRepeatLoginCode;
    }

    public String getAjaxRepeatLoginMsg() {
        return ajaxRepeatLoginMsg;
    }

    public void setAjaxRepeatLoginMsg(String ajaxRepeatLoginMsg) {
        this.ajaxRepeatLoginMsg = ajaxRepeatLoginMsg;
    }

    public String getPromptUrl() {
        return promptUrl;
    }

    public void setPromptUrl(String promptUrl) {
        this.promptUrl = promptUrl;
    }
}
