package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.admin.bean.dto.AddUserDTO;
import com.seven.admin.bean.dto.EditUserDTO;
import com.seven.admin.bean.dto.UserRoleDTO;
import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.bean.query.RoleQuery;
import com.seven.admin.bean.query.UserQuery;
import com.seven.admin.bean.vo.UserVO;
import com.seven.admin.constant.AdminStatCode;
import com.seven.admin.constant.SessionConstant;
import com.seven.admin.enums.DeleteEnum;
import com.seven.admin.mapper.SysUserMapper;
import com.seven.admin.service.SysUserPostService;
import com.seven.admin.service.SysUserRoleService;
import com.seven.admin.service.SysUserService;
import com.seven.admin.shiro.UserHolder;
import com.seven.admin.utils.ShiroUtils;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.enums.BetweenEnum;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.page.Page;
import com.seven.comm.core.page.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * 用户表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-21 15:15:48
 */

@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysUserPostService userPostService;

    @Override
    public SysUserEntity get(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public SysUserEntity getByUserName(String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUserEntity login(String username, String password) {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
            return null;
        }
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public PageInfo<UserVO> queryUserVoList(UserQuery user) {
        PageInfo<UserVO> pageInfo = new PageInfo<>();

        SevenQueryWrapper<SysUserEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("username", user.getUsername());
        queryWrapper.slikeLeft("phone", user.getPhone());
        queryWrapper.slike("email", user.getEmail());
        queryWrapper.seq("lock_flag", user.getLockFlag());
        queryWrapper.dateBetween("create_time", user.getBegin(), user.getEnd(), BetweenEnum.ALL_CONTAIN);

        Page<SysUserEntity> page = userMapper.selectByPage(queryWrapper, user.getPageNo(), user.getSize());
        List<SysUserEntity> list = page.getRows();
        if (CollectionUtils.isEmpty(list)) {
            return pageInfo;
        }
        List<UserVO> nList = new ArrayList<>(list.size());
        list.forEach(item -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(item, userVO);
            nList.add(userVO);
        });
        pageInfo.setRows(nList);
        return pageInfo;
    }

    @Override
    public Boolean addUser(AddUserDTO user) {
        if (checkLoginNameUnique(user.getUsername()) != null) {
            throw new SevenException(AdminStatCode.USERNAME_DUPLICATE.getMsg());
        }
        if (checkPhoneUnique(user.getPhone()) != null) {
            throw new SevenException(AdminStatCode.PHONE_DUPLICATE.getMsg());
        }
        if (checkEmailUnique(user.getEmail()) != null) {
            throw new SevenException(AdminStatCode.EMAIL_DUPLICATE.getMsg());
        }
        return addUserTransactional(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addUserTransactional(AddUserDTO user) {
        SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(user, userEntity, "userId");
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        userEntity.setDelFlag(DeleteEnum.NO_DELETE.getCode());
        userEntity.setSalt(ShiroUtils.randomSalt());
        if (StringUtils.isEmpty(userEntity.getPassword())) {
            userEntity.setPassword(SessionConstant.BASE_PASSWORD);
        }
        userEntity.setPassword(ShiroUtils.encryptPassword(userEntity.getUsername(), userEntity.getPassword(), userEntity.getSalt()));
        if (userMapper.insert(userEntity) == 1
                && userPostService.addUserPost(userEntity.getUserId(), user.getPostIds()) > 0
                && userRoleService.addUserRole(userEntity.getUserId(), user.getRoleIds()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public SysUserEntity checkLoginNameUnique(String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        SysUserEntity user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public SysUserEntity checkPhoneUnique(String phone) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        SysUserEntity user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public SysUserEntity checkEmailUnique(String email) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        SysUserEntity user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public Boolean updateUser(EditUserDTO user) {
        SysUserEntity userEntity = checkLoginNameUnique(user.getUsername());
        if (userEntity != null && !user.getUserId().equals(userEntity.getUserId())) {
            throw new SevenException(AdminStatCode.USERNAME_DUPLICATE.getMsg());
        }
        userEntity = checkPhoneUnique(user.getPhone());
        if (userEntity != null && !userEntity.getUserId().equals(user.getUserId())) {
            throw new SevenException(AdminStatCode.PHONE_DUPLICATE.getMsg());
        }
        userEntity = checkEmailUnique(user.getEmail());
        if (userEntity != null && !userEntity.getUserId().equals(user.getUserId())) {
            throw new SevenException(AdminStatCode.EMAIL_DUPLICATE.getMsg());
        }
        BeanUtils.copyProperties(user, userEntity, "createTime", "lockFlag");
        userEntity.setUpdateTime(new Date());
        return updateUserTransactional(user, userEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUserTransactional(EditUserDTO user, SysUserEntity userEntity) {
        //删除与角色关联
        userRoleService.deleteByUserId(userEntity.getUserId());
        //删除与岗位关联
        userPostService.deleteByUserId(userEntity.getUserId());
        //添加与角色关联
        userRoleService.addUserRole(userEntity.getUserId(), user.getRoleIds());
        //添加与岗位关联
        userPostService.addUserPost(userEntity.getUserId(), user.getPostIds());
        if (userMapper.updateById(userEntity) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean resetPwd(Integer userId) {
        if (userId == null || userId.equals(0)) {
            return false;
        }
        SysUserEntity userEntity = get(userId);
        if (Objects.isNull(userEntity)) {
            return false;
        }
        userEntity.setSalt(ShiroUtils.randomSalt());
        userEntity.setPassword(ShiroUtils.encryptPassword(userEntity.getUsername(), SessionConstant.BASE_PASSWORD, userEntity.getSalt()));
        userEntity.setUpdateTime(new Date());
        if (userMapper.updateById(userEntity) == 1) {
            if (UserHolder.getUserId() == userEntity.getUserId().intValue()) {
                UserHolder.setSysUser(userEntity);
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }
        for (Integer id : ids) {
            //删除用户与角色关联
            userRoleService.deleteByUserId(id);
            //删除用户与岗位关联
            userPostService.deleteByUserId(id);
            //删除用户
            userMapper.deleteById(id);
        }
        return true;
    }

    @Override
    public Boolean setAuthRole(UserRoleDTO roleDTO) {
        SysUserEntity userEntity = get(roleDTO.getUserId());
        if (Objects.isNull(userEntity)) {
            throw new SevenException(AdminStatCode.USER_NOT_FIND.getMsg());
        }
        if (userRoleService.addUserRole(roleDTO.getUserId(), roleDTO.getRoleIds()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean changeLockFlag(Integer userId, Integer lockFlag) {
        SysUserEntity userEntity = get(userId);
        if (Objects.isNull(userEntity)) {
            throw new SevenException(AdminStatCode.USER_NOT_FIND.getMsg());
        }
        userEntity.setLockFlag(String.valueOf(lockFlag));
        userEntity.setUpdateTime(new Date());
        userMapper.updateById(userEntity);
        return true;
    }

    @Override
    public PageInfo<UserVO> unassignedRoleList(UserQuery user) {
        if (user.getPageNo() == null || user.getPageNo() < 0) {
            user.setPageNo(1);
        }
        if (user.getSize() == null) {
            user.setSize(20);
        }
        int start = (user.getPageNo() - 1) * user.getSize();
        int total = userMapper.countUnassignedRole(user);
        if (total == 0) {
            return new PageInfo<>();
        }
        PageInfo<UserVO> pageInfo = new PageInfo<>();
        List<SysUserEntity> list = userMapper.unassignedRoleList(user, start, user.getSize());
        List<UserVO> nList = new ArrayList<>(list.size());
        list.forEach(item -> {
            UserVO roleVO = new UserVO();
            BeanUtils.copyProperties(item, roleVO);
            nList.add(roleVO);
        });
        pageInfo.setRows(nList);
        pageInfo.setTotal(total);
        pageInfo.setIndex(user.getPageNo());
        pageInfo.setSize(user.getSize());
        return pageInfo;
    }
}
