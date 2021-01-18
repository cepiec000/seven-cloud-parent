package com.seven.admin.service;

import com.seven.admin.bean.dto.LoginUser;
import com.seven.admin.constant.AdminConstants;
import com.seven.comm.core.async.AsyncManager;
import com.seven.admin.async.AsyncLogFactory;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisService redisService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        String verifyKey = AdminConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncLogFactory.loginLog(username, AdminConstants.LOGIN_FAIL, "token已过期"));
            throw new SevenException("token已过期");
        }
//        if (!code.equalsIgnoreCase(captcha)) {
//            AsyncManager.me().execute(AsyncLogFactory.loginLog(username, AdminConstants.LOGIN_FAIL, "token验证失败"));
//            throw new SevenException("token验证失败");
//        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncLogFactory.loginLog(username, AdminConstants.LOGIN_FAIL, "账户密码错误"));
                throw new SevenException("账户密码错误");
            } else {
                AsyncManager.me().execute(AsyncLogFactory.loginLog(username, AdminConstants.LOGIN_FAIL, e.getMessage()));
                e.printStackTrace();
                throw new SevenException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncLogFactory.loginLog(username, AdminConstants.LOGIN_SUCCESS, "登录成功"));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
