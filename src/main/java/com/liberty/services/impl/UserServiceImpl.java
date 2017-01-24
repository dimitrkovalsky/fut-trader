package com.liberty.services.impl;

import com.liberty.GlobalConfig;
import com.liberty.errors.ValidationException;
import com.liberty.model.User;
import com.liberty.repositories.UserRepository;
import com.liberty.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        if (!GlobalConfig.securityEnabled) {
            Optional<User> userOptional = userRepository.findOneByLogin(GlobalConfig.defaultUserLogin);
            if (userOptional.isPresent())
                return userOptional.get();
            else
                return null;
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    @Override
    public void createUser(User user) {
        if (userRepository.findOneByLogin(user.getLogin()).isPresent())
            throw new ValidationException("User with such login exists");
        userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findOneByLogin(login).orElseGet(null);
    }
}
