package com.seven.admin.shiro;

import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.comm.core.utils.PasswordUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * shiro 密码凭证匹配器 （验证密码有效性
 * @author v_chendongdong
 * @date 2020/12/21
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        SysUserEntity shiroUser = (SysUserEntity) info.getPrincipals().getPrimaryPrincipal();

        UsernamePasswordToken utoken=(UsernamePasswordToken)token;
        /**获取用户输入的密码**/
        String inPassword=String.valueOf(utoken.getPassword());
        /**数据库中的密码**/
        String dbPassword= (String) info.getCredentials();
        try{
            dbPassword= PasswordUtils.decrypt(dbPassword,shiroUser.getSalt());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        //进行密码的比对
        return this.equals(inPassword, dbPassword);
    }
}
