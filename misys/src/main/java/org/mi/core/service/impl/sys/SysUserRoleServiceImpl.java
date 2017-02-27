package org.mi.core.service.impl.sys;

import org.mi.core.dao.sys.SysUserRoleDao;
import org.mi.core.domain.security.SysUserRole;
import org.mi.core.service.sys.SysUserRoleService;
import org.springframework.stereotype.Service;

import com.feinno.framework.common.service.EntityServiceImpl;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends EntityServiceImpl<SysUserRole,SysUserRoleDao>  implements SysUserRoleService{

}
