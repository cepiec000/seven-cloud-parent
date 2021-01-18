package com.seven.mybatis.pagehelper;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 17:56
 */
@ConfigurationProperties(
        prefix = "pagehelper"
)
public class PageProperties {
    public static final String PAGEHELPER_PREFIX = "pagehelper";
    private Properties properties = new Properties();

    public PageProperties() {
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void setHelperDialect(String helperDialect) {
        this.properties.setProperty("helperDialect", helperDialect);
    }
}
