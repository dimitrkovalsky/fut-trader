package com.liberty.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Data
@Document(collection = "user_parameters")
public class UserParameters {

    @Id
    private ObjectId userId;

    private boolean autoBuyEnabled;
    private boolean autoSellEnabled;
}
