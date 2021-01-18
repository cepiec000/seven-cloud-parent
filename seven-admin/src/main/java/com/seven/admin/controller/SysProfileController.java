package com.seven.admin.controller;

import com.seven.admin.annotation.Log;
import com.seven.admin.bean.dto.LoginUser;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.service.SysUserService;
import com.seven.admin.service.TokenService;
import com.seven.admin.utils.SecurityUtils;
import com.seven.comm.core.enums.BusinessType;
import com.seven.comm.core.response.ApiResponse;
import com.seven.comm.web.utils.ServletUtils;
import com.seven.file.client.MinioTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息 业务处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    @Autowired
    private SysUserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MinioTemplate minioTemplate;

    /**
     * 个人信息
     */
    @GetMapping
    public ApiResponse profile() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUserEntity user = loginUser.getUser();
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("info", user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        ajax.put("postGroup", userService.selectUserPostGroup(loginUser.getUsername()));
        return ApiResponse.success(ajax);
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public ApiResponse updateProfile(@RequestBody SysUserEntity user) {
        if (userService.updateUserProfile(user) > 0) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            // 更新缓存用户信息
            loginUser.getUser().setNickName(user.getNickName());
            loginUser.getUser().setPhonenumber(user.getPhonenumber());
            loginUser.getUser().setEmail(user.getEmail());
            loginUser.getUser().setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return ApiResponse.success();
        }
        return ApiResponse.failed("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public ApiResponse updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return ApiResponse.failed("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return ApiResponse.failed("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return ApiResponse.success();
        }
        return ApiResponse.failed("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public ApiResponse avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            InputStream inputStream = file.getInputStream();
            minioTemplate.putObject("testbucket", file.getOriginalFilename(), inputStream);
            String avatar = minioTemplate.getObjectURL("testbucket", file.getOriginalFilename(), 7);
            inputStream.close();
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
                Map<String, Object> ajax = new HashMap<>();
                ajax.put("imgUrl", avatar);
                // 更新缓存用户头像
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return ApiResponse.success(ajax);
            }
        }
        return ApiResponse.failed("上传图片异常，请联系管理员");
    }
}
