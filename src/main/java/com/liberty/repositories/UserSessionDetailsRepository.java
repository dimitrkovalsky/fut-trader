package com.liberty.repositories;

import com.liberty.model.UserSessionDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Dmytro_Kovalskyi.
 * @since 19.11.2016.
 */
@Repository
public interface UserSessionDetailsRepository extends MongoRepository<UserSessionDetails, String> {
    Optional<UserSessionDetails> findByToken(String token);

    UserSessionDetails deleteByToken(String token);
}
