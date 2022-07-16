package cn.ncepu.alpl;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
@author xufuhang
@date 2022/2/2-8:23
*/
@SpringBootApplication
@MapperScan("cn.ncepu.alpl.dao")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
