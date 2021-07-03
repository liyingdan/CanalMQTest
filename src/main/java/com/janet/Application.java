package com.janet;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description 智能素材库项目启动入口
 * @Date 2020/7/31
 * @Author Janet
 */
@SpringBootApplication
@EnableCanalClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
