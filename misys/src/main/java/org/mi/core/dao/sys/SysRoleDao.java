package org.mi.core.dao.sys;

import org.mi.core.domain.security.SysRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;

/**
 * 系统角色表 JPA Dao
 *
 * Date: 2015-05-12 15:40:19
 *
 * @author Code Generator
 *
 */
public interface SysRoleDao extends EntityJpaDao<SysRole, Long> {


	@Query("from SysRole where roleName=?1 and id<>?2")
	public SysRole searchByName(String roleName,Long id);
	
	@Modifying
	@Query("delete SysRole where id=?1")
	public int deleteRole(Long id);
}
