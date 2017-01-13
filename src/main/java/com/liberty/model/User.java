package com.liberty.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liberty.common.Platform;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Data
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    private String login;
    private String email;
    private String password;
    private Platform platform;
    private int requestLimit;
    private int maxActivePlayersLimit;

    @JsonIgnore
    public String getStringId() {
        return id.toString();
    }
}
