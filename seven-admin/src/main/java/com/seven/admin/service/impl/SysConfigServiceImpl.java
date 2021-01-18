package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.admin.bean.entity.SysConfigEntity;
import com.seven.admin.bean.query.SysConfigQuery;
import com.seven.admin.constant.AdminConstants;
import com.seven.admin.constant.UserConstants;
import com.seven.admin.mapper.SysConfigMapper;
import com.seven.admin.service.SysConfigService;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.enums.BetweenEnum;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.redis.RedisService;
import com.seven.comm.core.text.Convert;
import com.seven.comm.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * 参数配置表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {
    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return AdminConstants.SYS_CONFIG_KEY + configKey;
    }

    @Override
    public SysConfigEntity selectConfigById(Long configId) {
        return configMapper.selectById(configId);
    }

    @Override
    public String selectConfigByKey(String configKey) {
        String configValue = Convert.toStr(redisService.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        SysConfigEntity retConfig = selectByKey(configKey);
        if (StringUtils.isNotNull(retConfig)) {
            redisService.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    private SysConfigEntity selectByKey(String configKey) {
        QueryWrapper<SysConfigEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_key", configKey);
        return configMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysConfigEntity> selectConfigList(SysConfigQuery config) {
        SevenQueryWrapper<SysConfigEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.slike("config_name", config.getConfigName());
        queryWrapper.seq("config_type", config.getConfigType());
        queryWrapper.slike("config_key", config.getConfigKey());
        queryWrapper.dateBetween("create_time", config, BetweenEnum.ALL_CONTAIN);
        return configMapper.selectList(queryWrapper);
    }

    @Override
    public int insertConfig(SysConfigEntity config) {
        int row = configMapper.insert(config);
        if (row > 0) {
            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    @Override
    public int updateConfig(SysConfigEntity config) {
        int row = configMapper.updateById(config);
        if (row > 0) {
            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    @Override
    public int deleteConfigByIds(Long[] configIds) {
        for (Long configId : configIds) {
            SysConfigEntity config = selectConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
                throw new SevenException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
        }
        int count = configMapper.deleteBatchIds(Arrays.asList(configIds));
        if (count > 0) {
            Collection<String> keys = redisService.keys(AdminConstants.SYS_CONFIG_KEY + "*");
            redisService.deleteObject(keys);
        }
        return count;
    }

    @Override
    public void clearCache() {
        Collection<String> keys = redisService.keys(AdminConstants.SYS_CONFIG_KEY + "*");
        redisService.deleteObject(keys);
    }

    @Override
    public String checkConfigKeyUnique(SysConfigEntity config) {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfigEntity info = checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    private SysConfigEntity checkConfigKeyUnique(String configKey) {
        QueryWrapper<SysConfigEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_key", configKey);
        queryWrapper.last(" limit 1");
        return configMapper.selectOne(queryWrapper);
    }
}
