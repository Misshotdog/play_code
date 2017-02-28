package org.mi.web.controller.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.mi.core.common.SessionFace;
import org.mi.core.common.SessionUser;
import org.mi.core.domain.security.SysUser;
import org.mi.core.service.sys.SysMenuService;
import org.mi.core.service.sys.SysResourceService;
import org.mi.core.service.sys.SysUserService;
import org.mi.security.realm.AuthRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/module/security")
public class SecurityIndexController {

	@Autowired
	private AuthRealm authRealm;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, Map<String, Object> map) {
		SessionUser sessionUser = SessionFace.getSessionUser(request);
		SysUser user = sysUserService.findByUsername(SecurityUtils.getSubject().getPrincipal().toString());
		Long classify = 4L;
		if (request.getParameter("classify") != null) {
			classify = Long.parseLong((String) request.getParameter("classify"));
			SessionFace.setAttribute(request, "CLASS_IFY", classify + "");
		} else if (SessionFace.getAttribute(request, "CLASS_IFY") != null) {
			if (!"0".equals((String) SessionFace.getAttribute(request, "CLASS_IFY"))) {
				classify = Long.parseLong((String) SessionFace.getAttribute(request, "CLASS_IFY"));
			}
		}
		SecurityUtils.getSubject().getSession().setAttribute("user",user);
		request.setAttribute("authorisedMenus", sysMenuService.queryAuthorisedMenus(sessionUser.getRole().getId(),classify));

		return "/module/security/index";
	}
	
	/**
	 * 
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityUtils.getSubject().logout();
		authRealm.clearAllCachedAuthorizationInfo();
		return "redirect:/login.html";
	}
}
