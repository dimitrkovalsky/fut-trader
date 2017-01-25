package com.liberty.repositories;

import com.liberty.model.UserTradeConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dmytro_Kovalskyi.
 * @since 19.11.2016.
 */
@Repository
public interface UserTradeConfigRepository extends MongoRepository<UserTradeConfig, String> {

}
