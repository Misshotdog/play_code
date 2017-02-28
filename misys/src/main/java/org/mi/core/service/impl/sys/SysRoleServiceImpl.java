package org.mi.core.service.impl.sys;



import org.mi.core.dao.sys.SysRoleDao;
import org.mi.core.domain.security.SysRole;
import org.mi.core.service.sys.SysRoleService;
import org.mi.security.cache.SecurityCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;

@Service("sysRoleService")
public class SysRoleServiceImpl extends EntityServiceImpl<SysRole, SysRoleDao> implements SysRoleService {

    private Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SecurityCache securityCache;
    
    @Override
    public void update(SysRole o) throws BusinessException {
        super.update(o);
        securityCache.clearAllAuthorization();
        securityCache.clearMenu(o.getId());
    }

}
