package com.liberty;

import com.liberty.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServerRunner {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(new Class<?>[]{Config.class}, args);

        System.out.println("Application started...");
    }
}
