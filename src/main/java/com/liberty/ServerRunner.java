package com.liberty;

import com.liberty.config.SpringMvcInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServerRunner {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(new Class<?>[]{SpringMvcInitializer.class}, args);

        System.out.println("Application started...");
    }
}
