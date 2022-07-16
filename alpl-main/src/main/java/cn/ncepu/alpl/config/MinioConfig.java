package cn.ncepu.alpl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xufuhang
 * @date 2022/4/22-23:26
 */
@Configuration
@ConfigurationProperties("minio")
@Data
public class MinioConfig {

    private String endpoint;
    private String bucketName;
    private String avatarBucket;
    private String accessKey;
    private String secretKey;

}
