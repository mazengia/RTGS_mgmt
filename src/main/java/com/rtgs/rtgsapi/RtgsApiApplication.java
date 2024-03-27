package com.rtgs.rtgsapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class RtgsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RtgsApiApplication.class, args);
    }

}
