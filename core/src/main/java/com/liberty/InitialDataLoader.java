package com.liberty;

import com.liberty.common.Platform;
import com.liberty.config.AppConfig;
import com.liberty.config.Config;
import com.liberty.config.SecurityConfig;
import com.liberty.model.User;
import com.liberty.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
public class InitialDataLoader {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(new Class<?>[]{Config.class, AppConfig.class,
                SecurityConfig.class}, args);

        createUser(context);
        System.out.println("Prices updated successfully");
        System.exit(0);
    }

    private static void createUser(ApplicationContext context) {
        UserService userService = context.getBean(UserService.class);
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        user.setEmail("test@test.com");
        user.setMaxActivePlayersLimit(50);
        user.setRequestLimit(10_000);
        user.setPlatform(Platform.PC);
        userService.createUser(user);
    }
}
