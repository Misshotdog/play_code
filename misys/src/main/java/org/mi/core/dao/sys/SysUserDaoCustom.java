package org.mi.core.dao.sys;

import java.util.Map;

import org.mi.core.domain.security.SysUser;

import com.feinno.framework.common.dao.support.PageInfo;

public interface SysUserDaoCustom {

	public PageInfo<SysUser> getInfoPage(PageInfo<SysUser> pageInfo, Object[] conditions,
			Map<String, Boolean> orderBy);
}
