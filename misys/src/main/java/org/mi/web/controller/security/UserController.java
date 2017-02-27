package org.mi.web.controller.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mi.core.domain.security.SysRole;
import org.mi.core.domain.security.SysUser;
import org.mi.core.service.sys.SysRoleService;
import org.mi.core.service.sys.SysUserService;
import org.mi.core.util.EncryptUtil;
import org.mi.web.controller.common.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feinno.framework.common.dao.support.PageInfo;

@Controller
@RequestMapping("/module/user")
public class UserController extends CommonController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	
	int HASH_INTERATIONS = 1024;
	int SALT_SIZE = 8;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="userList")
	public String userList(HttpServletRequest request){
	   List<Object> paramList = new ArrayList<Object>();
       Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
	   PageInfo<SysUser> pageInfo = sysUserService.getInfoPage(getPageInfo(request), paramList.toArray(), sortMap);
	   request.setAttribute("pageInfo", pageInfo);
	   return "/module/security/user_list";
	}
	
	
	@RequestMapping(value="userAdd")
	public String userAdd(HttpServletRequest request){
		List<SysRole> roleList = sysRoleService.getAll();
		request.setAttribute("roleList", roleList);
		return "/module/security/user_add";
	}
	
	
	
	private void entryptPassword(SysUser user) {
        user.setPassword(EncryptUtil.sha1(user.getPassword(), EncryptUtil.generatorSalt(SALT_SIZE), HASH_INTERATIONS));
	}
}
