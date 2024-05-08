package com.rtgs.rtgsapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RtgsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RtgsApiApplication.class, args);
    }

}
