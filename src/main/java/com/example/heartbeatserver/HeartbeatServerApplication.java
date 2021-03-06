package com.example.heartbeatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@ServletComponentScan("com.example.heartbeatserver.filter")
public class HeartbeatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartbeatServerApplication.class, args);
    }

}
