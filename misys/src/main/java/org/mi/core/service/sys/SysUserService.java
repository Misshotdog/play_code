package org.mi.core.service.sys;


import java.util.Map;

import org.mi.core.domain.security.SysUser;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityService;

/**
 * 系统用户表 Service
 *
 *
 * @author miss_hotdog
 *
 */
public interface SysUserService extends EntityService<SysUser> {

	public SysUser findByUsername(String username);
	
	public PageInfo<SysUser> getInfoPage(PageInfo<SysUser> pageInfo, Object[] conditions,
			Map<String, Boolean> orderBy);
}
