package com.test.shiro.ch02.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Author: Xiang Liguo<br />
 * Date: 2019/08/29 16:11<br />
 * Version: v1.0<br />
 * Description: 自定义 realm 实现类<br />
 * Update: <br />
 */
public class MyRealm implements Realm {
    @Override
    public String getName() {
        return "MyRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        // 是否支持某种 token，这里只定义为用户名和密码方式
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        if (!"zhang".equals(userName)) {
            throw new UnknownAccountException("user name not exists.");
        }
        if (!"123".equals(password)) {
            throw new IncorrectCredentialsException("password incorrect.");
        }

        // 如果认证成功，返回一个 AuthenticationInfo 实现，封装了用户名和密码
        return new SimpleAuthenticationInfo(userName, password, getName());
    }
}
