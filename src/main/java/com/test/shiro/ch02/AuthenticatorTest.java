package com.test.shiro.ch02;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

/**
 * Author: Xiang Liguo<br />
 * Date: 2019/08/29 16:45<br />
 * Version: v1.0<br />
 * Description: 验证规则<br />
 * Update: <br />
 */
public class AuthenticatorTest {

    public void login(String iniFile) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(iniFile);
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);
    }

    @Test
    public void testAllSuccess() {
        login("classpath:shiro.authenticator.all.success.ini");
        Subject subject = SecurityUtils.getSubject();

        PrincipalCollection collection = subject.getPrincipals();
        if (collection.asList().size() == 2) {
            System.out.println("all success.");
        } else {
            System.out.println("not all success.");
        }
    }

    @After
    public void after() {
        // 从当前线程解绑 subject
        ThreadContext.unbindSubject();
    }
}
