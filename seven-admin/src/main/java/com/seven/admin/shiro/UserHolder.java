package com.seven.admin.shiro;

import com.seven.admin.bean.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * 获取当前登录账号
 * @author v_chendongdong
 * @date 2020/12/22
 */
public class UserHolder {

    public static Subject getSubject()
    {
        return SecurityUtils.getSubject();
    }

    public static Session getSession()
    {
        return SecurityUtils.getSubject().getSession();
    }

    public static int getUserId() {
        ShiroUser user = getUser();
        if (Objects.isNull(user)) {
            return -1;
        }
        return user.getUserId();
    }

    public static int getUserDeptId() {
        ShiroUser user = getUser();
        if (Objects.isNull(user)) {
            return -1;
        }
        return user.getDeptId();
    }

    public static ShiroUser getUser() {
        ShiroUser user = (ShiroUser) getSubject().getPrincipal();
        return user;
    }

    public static void setSysUser(ShiroUser user)
    {
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    public static void setSysUser(SysUserEntity user)
    {
        ShiroUser shiroUser=new ShiroUser();
        BeanUtils.copyProperties(user,shiroUser);
        setSysUser(shiroUser);
    }

    public static String getUserName()
    {
        return getUser().getUsername();
    }

    public static String getIp()
    {
        return getSubject().getSession().getHost();
    }

    public static String getSessionId()
    {
        return String.valueOf(getSubject().getSession().getId());
    }




}
