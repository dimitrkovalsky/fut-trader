package com.liberty.services;

import com.liberty.model.PlayerId;
import com.liberty.model.PlayerTradeStatus;

import java.util.Map;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
public interface UserTradeConfigService {
    void deletePlayerToAutoBuy(PlayerId playerId);

    void addPlayerToAutoBuy(PlayerTradeStatus tradeStatus);

    void updatePlayer(PlayerTradeStatus request);

    void disableAll();

    void enablePlayer(PlayerId playerId, boolean enabled);

    Map<PlayerId, PlayerTradeStatus> getAllTrades();
}
