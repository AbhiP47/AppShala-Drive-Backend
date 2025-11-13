package com.DriveService.DriveService;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DriveServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(DriveServiceApplication.class, args);
    }
}
