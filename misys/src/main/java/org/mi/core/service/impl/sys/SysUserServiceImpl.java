package org.mi.core.service.impl.sys;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.mi.core.common.SessionFace;
import org.mi.core.common.SessionUser;
import org.mi.core.dao.sys.SysUserDao;
import org.mi.core.dao.sys.SysUserDaoCustom;
import org.mi.core.domain.security.SysResource;
import org.mi.core.domain.security.SysUser;
import org.mi.core.service.sys.SysUserService;
import org.mi.security.cache.SecurityCache;
import org.mi.security.support.SubjectInfo;
import org.mi.security.support.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;

@Service("sysUserService")
public class SysUserServiceImpl extends EntityServiceImpl<SysUser, SysUserDao> implements SysUserService, SubjectService {

    private Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SecurityCache securityCache;
    @Autowired
    private SysUserDaoCustom sysUserDaoCustom;
   

    public void update(SysUser o) throws BusinessException {
        super.update(o);
        try{
            securityCache.clearAuthorization(o.getUsername());
            SessionUser sessionUser = SessionFace.getOnlineSessionUser(o.getUsername());
            if(sessionUser!=null){
                sessionUser.update(o);
            }
        }catch (Exception ex){
            logger.error("", ex);
        }
    }


	@Override
	public SysUser findByUsername(String username) {
		return this.getEntityDao().findByUsername(username);
	}


	public boolean checkUsername(String username) {
		if(super.getEntityDao().findByUsername(username) != null){
			return true;
		}else{
			return false;
		}
	}

	public SubjectInfo getSubject(String userName) {
		SubjectInfo subjectInfo = new SubjectInfo();
		SysUser sysUser = this.findByUsername(userName);
		String ipAddress = SecurityUtils.getSubject().getSession().getHost();
		if (sysUser == null) {
			return null;
		} else {
//			log.setIpAddress(ipAddress);
//			log.setLogonId(UUID.randomUUID().toString());
//			log.setLoginName(sysUser.getUserName());
//			log.setUserName(sysUser.getTruename());
//			log.setLogonTime(new Date());
//			log.setLogonFlag("1");
			subjectInfo.setPassword(sysUser.getPassword());
			subjectInfo.setPwdSalt(sysUser.getSalt());
			Date dt = new Date();
			if (sysUser.getStatus() == 0) {
				//log.setLogfailedReason("账号输入错误");
				//log.setLogonFlag("0");
				subjectInfo.setResult(1);
			} else if (sysUser.getStatus() == 2) {
				//log.setLogfailedReason("账号被锁定");
				//log.setLogonFlag("0");
				subjectInfo.setResult(2);
			} else if (sysUser.getExpiredDate() != null && sysUser.getExpiredDate().getTime() < dt.getTime()){
				//log.setLogonFlag("0");
				//log.setLogfailedReason("账号已过期");
				subjectInfo.setResult(3);
			}
		}
		//sysLogonlogDao.save(log);
		return subjectInfo;
	}

	public List<String> listRole(String userName) {
        SysUser sysUser = this.findByUsername(userName);
        if(sysUser!=null && sysUser.getRole() !=null){
            return Arrays.asList(sysUser.getRole().getRoleName());
        }
        return null;
    }

    public List<String> listPermission(String userName) {
        SysUser sysUser = this.findByUsername(userName);
        if(sysUser!=null && sysUser.getRole() !=null && sysUser.getRole().getRescs()!=null) {
            List<String> resList = new ArrayList<String>();
            for(SysResource resource : sysUser.getRole().getRescs()){
                resList.add(resource.getPermissionValue());
            }
            return resList;
        }
        return null;
    }


	public List<SysUser> findByRole(Long roleId) {
		return null;
	}

	public PageInfo<SysUser> getInfoPage(PageInfo<SysUser> pageInfo, Object[] conditions,
			Map<String, Boolean> orderBy) {
		return sysUserDaoCustom.getInfoPage(pageInfo, conditions, orderBy);
	}
}
