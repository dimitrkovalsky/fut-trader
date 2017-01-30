package com.liberty.services;

import com.liberty.model.AuctionInfo;
import com.liberty.model.TradeStatus;

import java.util.Optional;

/**
 * @author Dmytro_Kovalskyi.
 * @since 25.01.2017.
 */
public interface RequestService {
    Optional<TradeStatus> searchPlayer(Long playerId, int rareFlag, Integer buyPrice);

    boolean buy(AuctionInfo auctionInfo);
}
