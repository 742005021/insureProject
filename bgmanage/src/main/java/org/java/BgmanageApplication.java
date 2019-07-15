package org.java;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BgmanageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BgmanageApplication.class, args);
    }

//    @Bean
//    public RuntimeService runtimeService(){
//        return new RuntimeServiceImpl();
//    }
}
