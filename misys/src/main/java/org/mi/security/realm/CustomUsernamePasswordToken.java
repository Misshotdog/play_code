package org.mi.security.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by Administrator on 2015/11/13 0013.
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {




    public CustomUsernamePasswordToken(String username, String password, boolean rememberMe, String host) {
        //璋冪敤鐖剁被鐨勬瀯閫犲嚱鏁�
        super(username, password, rememberMe, host);
    }



}
