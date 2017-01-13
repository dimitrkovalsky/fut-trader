package com.liberty.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Data
@Document(collection = "user_trade_configs")
public class UserTradeConfig {

    @Id
    private String userId;

    private Set<PlayerTradeStatus> players = new HashSet<>();

    public void addPlayer(PlayerTradeStatus tradeStatus) {
        players.add(tradeStatus);
    }
}
