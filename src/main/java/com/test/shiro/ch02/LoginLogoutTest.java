package com.test.shiro.ch02;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

/**
 * Author: Xiang Liguo<br />
 * Date: 2019/08/29 13:32<br />
 * Version: v1.0<br />
 * Description: Shiro 登录登出测试<br />
 * Update: <br />
 */
public class LoginLogoutTest {

    @Test
    public void test() {
        // 1、获取 SecurityManager 工厂类实例
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        // 2、得到实例并注入到 SecurityUtils 中
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        // 3、得到 Subject 和用户登录密码 token
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        // 4、登录
        try {
            subject.login(token);
            System.out.println("login success");
        } catch (Exception e) {
            // 登录失败
            e.printStackTrace();
            System.out.println("login failed.");
        }

        Assert.isTrue(subject.isAuthenticated());

        subject.logout();
        System.out.println("logout");
    }


    @Test
    public void testRealm() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);

        if (subject.isAuthenticated()) {
            System.out.println("login success.");
        } else {
            System.out.println("login failed.");
        }

        subject.logout();
    }

    @Test
    public void testJdbcRealm() {
        // 通过 ini 文件创建 factory 工厂实例
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
        // 将 SecurityManager 注入到 SecurityUtils 中
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        // 得到 subject
        Subject subject = SecurityUtils.getSubject();
        // 创建用户名和密码的 token
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        // 尝试登录
        try {
            subject.login(token);
        } catch (Exception e) {
            // 登录失败
            e.printStackTrace();
        }

        // 断言是否登录成功
        if (subject.isAuthenticated()) {
            System.out.println("login success.");
        } else {
            System.out.println("login failed.");
        }

        // 退出
        subject.logout();
    }

    @After
    public void after() {
        // 退出时解绑 subject 与线程的关系，否则下次会有问题
        ThreadContext.unbindSubject();
    }
}
