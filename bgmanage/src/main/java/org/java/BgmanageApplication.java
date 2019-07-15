package org.java;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BgmanageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BgmanageApplication.class, args);
    }

}
