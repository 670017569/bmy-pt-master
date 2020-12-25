package com.bmy.oss.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Created by f9miao
 */
@Configuration
public class MinioConfig {

    Logger logger = LoggerFactory.getLogger(MinioConfig.class);

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.host}")
    private String host;

    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        logger.info(String.format("host: %s", host));
        logger.info(String.format("accessKey: %s", accessKey));
        logger.info(String.format("secretKey: %s", secretKey));
        return new MinioClient(host, accessKey, secretKey);
    }

}
