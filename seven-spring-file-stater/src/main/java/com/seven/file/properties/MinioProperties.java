package com.seven.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("ConfigurationProperties")
@Data
@ConfigurationProperties("min.io")
public class MinioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
