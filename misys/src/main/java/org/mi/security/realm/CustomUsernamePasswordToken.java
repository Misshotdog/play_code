package org.mi.security.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by miss_hotdog
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {




    public CustomUsernamePasswordToken(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }



}
