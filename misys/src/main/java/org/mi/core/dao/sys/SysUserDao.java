package org.mi.core.dao.sys;

import org.mi.core.domain.security.SysUser;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;

/**
 * 系统用户表 JPA Dao
 *
 * Date: 2015-05-11 16:11:20
 *
 * @author Code Generator
 *
 */
public interface SysUserDao extends EntityJpaDao<SysUser, Long> {

	public SysUser findByUsername(String username);

}
