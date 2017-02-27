package org.mi.core.service.sys;


import org.mi.core.domain.security.SysRole;

import com.feinno.framework.common.service.EntityService;

/**
 * 系统角色表 Service
 *
 * Date: 2015-05-12 15:40:19
 *
 * @author Code Generator
 *
 */
public interface SysRoleService extends EntityService<SysRole> {


	public SysRole searchByName(String roleName,Long id);
	
	public int deleteRole(Long id);
}
