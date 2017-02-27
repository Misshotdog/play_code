package org.mi.core.dao.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.mi.core.dao.sys.SysUserDaoCustom;
import org.mi.core.domain.security.SysUser;
import org.springframework.stereotype.Repository;

import com.feinno.framework.common.dao.jdbc.PagedJdbcTemplate;
import com.feinno.framework.common.dao.support.PageInfo;

@Repository
public class SysUserDaoImpl implements SysUserDaoCustom{
	
	@Resource(name="pagedJdbcTemplate")
	private PagedJdbcTemplate  pagedJdbcTemplate;

	public PageInfo<SysUser> getInfoPage(PageInfo<SysUser> pageInfo, Object[] conditions,
			Map<String, Boolean> orderBy) {
		String sql = "select a.*,c.role_name as roleName from sys_user a "
				+ "join sys_user_role b on a.id = b.user_id "
				+ "join sys_role c on c.id=b.role_id";
		return pagedJdbcTemplate.queryMySql(pageInfo, sql, conditions, SysUser.class);
	}

}
