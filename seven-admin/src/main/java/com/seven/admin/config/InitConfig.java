package com.seven.admin.config;

import com.seven.admin.bean.entity.SysConfigEntity;
import com.seven.admin.bean.entity.SysDictDataEntity;
import com.seven.admin.bean.entity.SysDictTypeEntity;
import com.seven.admin.bean.query.SysConfigQuery;
import com.seven.admin.constant.AdminConstants;
import com.seven.admin.service.SysConfigService;
import com.seven.admin.service.SysDictDataService;
import com.seven.admin.service.SysDictTypeService;
import com.seven.admin.utils.DictUtils;
import com.seven.comm.core.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2021/1/8 18:19
 */
@Component
public class InitConfig implements CommandLineRunner {

    @Autowired
    private SysConfigService configService;
    @Autowired
    private SysDictTypeService dictTypeService;
    @Autowired
    private SysDictDataService dictDataService;

    @Autowired
    private RedisService redisService;

    /**
     * 项目启动时，初始化参数到缓存
     */
    public void initConfig() {
        redisService.setCacheObject("cdd", "123123");
        List<SysConfigEntity> configsList = configService.selectConfigList(new SysConfigQuery());
        for (SysConfigEntity config : configsList) {
            redisService.setCacheObject(AdminConstants.SYS_CONFIG_KEY + config.getConfigKey(), config.getConfigValue());
        }
    }

    /**
     * 项目启动时，初始化字典到缓存
     */
    public void initDict() {
        List<SysDictTypeEntity> dictTypeList = dictTypeService.selectDictTypeAll();
        for (SysDictTypeEntity dictType : dictTypeList) {
            List<SysDictDataEntity> dictDatas = dictDataService.selectDictDataByType(dictType.getDictType());
            DictUtils.setDictCache(dictType.getDictType(), dictDatas);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        initConfig();
        initDict();
    }
}
