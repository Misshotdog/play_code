package org.mi.security.filter;

import com.feinno.framework.common.web.support.JsonResult;
import com.feinno.framework.utils.mapper.JsonMapper;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by Administrator on 
 */
public class UrlResourceAuthorizationFilter extends AuthorizationFilter {

    private Logger logger = LoggerFactory.getLogger(UrlResourceAuthorizationFilter.class);

    private String ajaxNotAuthCode="FEINNO_SECURITY2_NOT_AUTH";

    private String ajaxNotAuthMsg="对不起您没有操作权限";


    /**
     * 获取访问的url并进行权限认证
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 获取页面url
        String permission = httpRequest.getRequestURI();
        boolean isPermitted = subject.isPermitted(permission);
        logger.trace("url {} is permitted {}", permission, isPermitted);
        return isPermitted;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //没有权限的处理
        //如果为ajax返回json否则跳转无权限页面
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if ((httpRequest.getHeader("x-requested-with") != null && httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
                || (httpRequest.getRequestURI()!=null && httpRequest.getRequestURI().indexOf("ajax")!=-1)) {
            //httpResponse.setHeader("Content-Type", "text/html;charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JsonMapper.nonDefaultMapper().toJson(new JsonResult(ajaxNotAuthCode, ajaxNotAuthMsg)));
            printWriter.flush();
            printWriter.close();
            return false;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }


    public String getAjaxNotAuthCode() {
        return ajaxNotAuthCode;
    }

    public void setAjaxNotAuthCode(String ajaxNotAuthCode) {
        this.ajaxNotAuthCode = ajaxNotAuthCode;
    }

    public String getAjaxNotAuthMsg() {
        return ajaxNotAuthMsg;
    }

    public void setAjaxNotAuthMsg(String ajaxNotAuthMsg) {
        this.ajaxNotAuthMsg = ajaxNotAuthMsg;
    }
}
