package cn.ncepu.alpl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
@author xufuhang
@date 2022/1/8-0:27
*/
@Configuration
@ConfigurationProperties(prefix = "upload")
@Data
public class UploadConfig {

    private String imagePath;

    private String videoPath;

}
