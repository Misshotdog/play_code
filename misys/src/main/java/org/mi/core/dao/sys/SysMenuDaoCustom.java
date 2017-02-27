package org.mi.core.dao.sys;

import java.util.List;
import java.util.Map;

import org.mi.core.domain.security.SysMenu;

import com.feinno.framework.common.dao.support.PageInfo;

/** 
 * 
 * File Name:SysMenuDaoCustom.java 
 * Package Name:com.feinno.bms.core.dao.sys 
 * Date:2016年7月25日下午8:52:16 
 * Copyright (c) BeiJinghtdf 2016, All Rights Reserved. 
 * 
*/
public interface SysMenuDaoCustom {

	public PageInfo<SysMenu> getInfoPage(PageInfo<SysMenu> pageInfo,
			Map<String, Object> conditions, Map<String, Boolean> orderBy);
	
	
	public List<SysMenu>  getMenuList();
}
