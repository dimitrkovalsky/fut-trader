package com.liberty.services;

import com.liberty.model.User;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
public interface UserService {
    User getCurrentUser();

    void createUser(User user);
}
