package com.liberty.services.impl;

import com.liberty.GlobalConfig;
import com.liberty.errors.ValidationException;
import com.liberty.model.User;
import com.liberty.model.UserParameters;
import com.liberty.model.UserTradeConfig;
import com.liberty.repositories.UserParameterRepository;
import com.liberty.repositories.UserRepository;
import com.liberty.repositories.UserTradeConfigRepository;
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

    @Autowired
    private UserParameterRepository userParameterRepository;

    @Autowired
    private UserTradeConfigRepository tradeConfigRepository;

    @Override
    public User getCurrentUser() {
        if (!GlobalConfig.securityEnabled) {
            Optional<User> userOptional = userRepository.findOneByLogin(GlobalConfig.defaultUserLogin);
            return userOptional.orElse(null);
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void createUser(User user) {
        if (userRepository.findOneByLogin(user.getLogin()).isPresent())
            throw new ValidationException("User with such login exists");
        User saved = userRepository.save(user);
        createUserParameters(saved);
        createUserTradeConfig(saved);
    }

    private void createUserTradeConfig(User saved) {
        UserTradeConfig config = new UserTradeConfig();
        config.setUserId(saved.getStringId());
        tradeConfigRepository.save(config);
    }

    private void createUserParameters(User saved) {
        UserParameters userParameters = new UserParameters();
        userParameters.setUserId(saved.getId());
        userParameters.setAutoBuyEnabled(true);
        userParameters.setAutoSellEnabled(false);
        userParameterRepository.save(userParameters);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findOneByLogin(login).orElseGet(null);
    }
}
