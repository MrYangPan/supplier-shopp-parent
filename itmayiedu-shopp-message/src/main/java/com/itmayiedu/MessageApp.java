package com.itmayiedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by Mr.PanYang on 2018/8/10.
 */
@SpringBootApplication
@EnableEurekaClient
public class MessageApp {
    public static void main(String[] args) {
        SpringApplication.run(MessageApp.class, args);
    }
}
