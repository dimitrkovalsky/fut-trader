package com.liberty.repositories;

import com.liberty.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Dmytro_Kovalskyi.
 * @since 19.11.2016.
 */
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findOneByLogin(String login);
}
