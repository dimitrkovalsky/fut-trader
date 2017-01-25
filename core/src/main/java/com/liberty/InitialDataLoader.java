package com.liberty;

import com.liberty.common.Platform;
import com.liberty.config.Config;
import com.liberty.model.User;
import com.liberty.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
public class InitialDataLoader {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
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
