package com.itmayiedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by Mr.PanYang on 2018/8/14.
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class PCWebApp {

    public static void main(String[] args) {
        SpringApplication.run(PCWebApp.class, args);
    }

}
