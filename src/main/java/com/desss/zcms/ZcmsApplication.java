package com.desss.zcms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZcmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZcmsApplication.class, args);
    }
}
