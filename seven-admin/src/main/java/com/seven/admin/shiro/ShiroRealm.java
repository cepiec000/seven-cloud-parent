package com.seven.admin.shiro;

import com.seven.admin.constant.AdminStatCode;
import com.seven.admin.bean.entity.SysMenuEntity;
import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.enums.UserLockEnum;
import com.seven.admin.service.SysMenuService;
import com.seven.admin.service.SysRoleService;
import com.seven.admin.service.SysUserService;
import com.seven.admin.utils.ShiroUtils;
import com.seven.comm.core.execption.SevenException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * shiro 权限设置 权限判断
 *
 * @author v_chendongdong
 * @date 2020/12/23
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Resource
    private SysUserService userService;
    @Resource
    private SysMenuService menuService;
    @Resource
    private SysRoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getUserId();

        // 赋予角色
        List<SysRoleEntity> roleList = roleService.queryRolesByUserId(userId);
        roleList.forEach(role -> {
            info.addRole(role.getRoleName());
        });

        // 赋予权限
        List<SysMenuEntity> menuList = menuService.queryMenusByUserId(userId);
        if (!CollectionUtils.isEmpty(menuList)) {
            log.info("<<<<用户{} 存在以下权限>>>>", userId);
            for (SysMenuEntity menu : menuList) {
                String permission = menu.getPermission();
                log.info(menu.getName() + "   " + permission);
                if (!StringUtils.isEmpty(permission)) {
                    info.addStringPermission(permission);
                }
            }
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        SysUserEntity dbUser = userService.getByUserName(username);
        if (Objects.isNull(dbUser)) {
            throw new SevenException(AdminStatCode.USER_NOT_FIND.getMsg());
        }
        String inPassword = ShiroUtils.encryptPassword(dbUser.getUsername(), password, dbUser.getSalt());
        SysUserEntity user = userService.login(username, inPassword);
        if (user == null) {
            throw new UnknownAccountException(AdminStatCode.TOKEN_BE_EMPTY.getMsg());
        }
        if (user.getLockFlag() != null && UserLockEnum.LOCKED.getCode().equals(user.getLockFlag())) {
            throw new LockedAccountException(AdminStatCode.USER_ON_LOCKED.getMsg());
        }
        ShiroUser shiroUser = new ShiroUser();
        BeanUtils.copyProperties(user, shiroUser);
        // principal参数使用用户Id，方便动态刷新用户权限
        return new SimpleAuthenticationInfo(
                shiroUser,
                password,
                getName()
        );
    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
