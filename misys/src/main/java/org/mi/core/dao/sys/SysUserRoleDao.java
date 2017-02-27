package org.mi.core.dao.sys;

import org.mi.core.domain.security.SysUserRole;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;

/** 
 * 
 * File Name:SysUserRoleDao.java 
 * Package Name:com.feinno.bms.core.dao.sys 
 * Date:2016年7月24日下午1:17:51 
 * Copyright (c) BeiJinghtdf 2016, All Rights Reserved. 
 * 
*/
public interface SysUserRoleDao extends EntityJpaDao<SysUserRole,Long>{

	
	public void saveUserRole(SysUserRole userRole);
}
