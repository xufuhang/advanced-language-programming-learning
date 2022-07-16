package cn.ncepu.alpl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/*
@author xufuhang
@date 2022/3/26-21:50
*/
@ConfigurationProperties(prefix = "security")
@Data
public class IgnoreUrlConfig {

    private List<String> ignoreUrls;

}
