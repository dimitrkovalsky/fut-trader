package com.liberty;

import com.liberty.config.AppConfig;
import com.liberty.config.Config;
import com.liberty.config.SecurityConfig;
import com.liberty.config.quartz.JobConfig;
import com.liberty.config.quartz.QuartzConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServerRunner {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(new Class<?>[]{Config.class, AppConfig.class,
                SecurityConfig.class, QuartzConfig.class, JobConfig.class}, args);

        System.out.println("Application started...");
    }
}
