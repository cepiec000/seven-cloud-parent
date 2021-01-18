package com.seven.file.autoconfigure;

import com.seven.file.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.seven.file.client.MinioTemplate;

@Slf4j
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioAutoConfiguration {
    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient buildClient() {
        MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
        return minioClient;
    }

    @Bean
    public MinioTemplate createTemplate(MinioClient client) {
        return new MinioTemplate(client);
    }
}
