package com.liberty.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Data
@Document(collection = "user_trade_configs")
public class UserTradeConfig {

    @Id
    private String userId;

    private Map<PlayerId, PlayerTradeStatus> players = new HashMap<>();

    public void addPlayer(PlayerTradeStatus tradeStatus) {
        players.put(tradeStatus.getPlayerId(), tradeStatus);
    }
}
