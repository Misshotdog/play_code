package org.mi.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mi.core.common.jdbc.QueryPageMSSqlExt;
import org.mi.core.dao.sys.SysMenuDaoCustom;
import org.mi.core.domain.security.SysMenu;
import org.springframework.stereotype.Repository;

import com.feinno.framework.common.dao.jdbc.PagedJdbcTemplate;
import com.feinno.framework.common.dao.support.PageInfo;

/** 
 * 
 * File Name:SysMenuDaoImpl.java 
 * Package Name:com.feinno.bms.core.dao.impl.sys 
 * Date:2016年7月26日上午8:23:59 
 * Copyright (c) BeiJinghtdf 2016, All Rights Reserved. 
 * 
*/
@Repository
public class SysMenuDaoImpl implements SysMenuDaoCustom{

	@Resource(name="pagedJdbcTemplate")
	private PagedJdbcTemplate  pagedJdbcTemplate;
	@Resource
	QueryPageMSSqlExt queryPageMSSqlExt;
	
	public PageInfo<SysMenu> getInfoPage(PageInfo<SysMenu> pageInfo, Map<String, Object> conditions,
			Map<String, Boolean> orderBy) {
		StringBuffer sql  = new StringBuffer();
		sql.append("SELECT menu.*, res.TITLE as resTitle ,res.RESTYPE,res.RES_STRING,res.id as resId");
		sql.append(" FROM SYS_MENU menu left JOIN SYS_RESOURCE res ON menu.RESOURCE_ID=res.id");
		queryPageMSSqlExt.query(sql.toString(), conditions, orderBy, pageInfo, SysMenu.class, pagedJdbcTemplate);
		return pageInfo;
	}

	public List<SysMenu> getMenuList() {
		StringBuffer sql  = new StringBuffer();
		sql.append("SELECT menu.*, res.TITLE as resTitle ,res.RESTYPE,res.RES_STRING,res.id as resId");
		sql.append(" FROM SYS_MENU menu left JOIN SYS_RESOURCE res ON menu.RESOURCE_ID=res.id");
		return pagedJdbcTemplate.queryForList(sql.toString(), SysMenu.class);
	}

}
