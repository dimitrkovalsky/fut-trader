package com.liberty.services.impl;

import com.liberty.model.AuctionInfo;
import com.liberty.model.TradeStatus;
import com.liberty.services.RequestService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Dmytro_Kovalskyi.
 * @since 25.01.2017.
 */
@Service
public class ServerSideRequestServiceImpl implements RequestService {

    @Override
    public Optional<TradeStatus> searchPlayer(Long playerId, int rareFlag, Integer buyPrice) {
        return Optional.empty();
    }

    @Override
    public boolean buy(AuctionInfo auctionInfo) {
        return false;
    }
}
