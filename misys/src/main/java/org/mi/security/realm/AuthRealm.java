package org.mi.security.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.mi.security.support.Base64;
import org.mi.security.support.SubjectInfo;
import org.mi.security.support.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by miss_hotdog.
 */
public class AuthRealm extends AuthorizingRealm {


    private Logger logger = LoggerFactory.getLogger(AuthRealm.class);

    private SubjectService subjectService;

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清空指定用户的授权缓存
     * @param userName
     */
    public void clearCachedAuthorizationInfo(String userName){
        //TODO 单独删除有问题，不知道原因，统一全部清除
        /*Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if(cache!=null) {
            cache.remove(userName);
        }*/
        clearAllCachedAuthorizationInfo();
    }

    /**
     * 清空所有用户的授权缓存
     */
    public void clearAllCachedAuthorizationInfo(){
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if(cache!=null) {
            cache.clear();
        }
    }

    /**
     * 登陆认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SubjectInfo subject = subjectService.getSubject(token.getUsername());
        if(subject==null){
            throw new AuthenticationException("username \""+token.getUsername()+"\" is not found user");
        }
        
        switch (subject.getResult()) {
        	case 1:
        		throw new org.mi.security.exception.UnknownAccountException();
			case 2:
				throw new org.mi.security.exception.AccountLockedException();
			case 3:
				throw new org.mi.security.exception.AccountExpiredException();
        }
        
        char[] chpassword= new char[0];
        try {
            chpassword = Base64.base64Decode(String.valueOf(token.getPassword())).toCharArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        token.setPassword(chpassword);
     /*   if(subject.getPassword().equals(String.valueOf(token.getPassword()))){
            throw new AuthenticationException("password \""+token.getPassword()+"\" is  error");
        }*/
        SimpleAuthenticationInfo saInfo = new SimpleAuthenticationInfo(token.getPrincipal(),
                subject.getPassword(),
                subject.getPwdSalt() != null ? ByteSource.Util.bytes(subject.getPwdSalt()) : null,
                getName());
        return saInfo;
    }
    public static char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }
    /**
     * 用户授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //授予角色
        info.addRoles(subjectService.listRole(userName));
        //授予权限
        info.addStringPermissions(subjectService.listPermission(userName));

        logger.debug("User Name:{}, authorization info Roles:{}, Permissions:{}",
                userName,
                info.getRoles().toString(),
                info.getStringPermissions().toString());
        return info;
    }



    public SubjectService getSubjectService() {
        return subjectService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
}
