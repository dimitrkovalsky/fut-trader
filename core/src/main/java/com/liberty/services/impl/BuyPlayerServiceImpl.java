package com.liberty.services.impl;

import com.google.common.collect.Lists;
import com.liberty.common.DelayHelper;
import com.liberty.model.*;
import com.liberty.services.BuyPlayerService;
import com.liberty.services.RequestService;
import com.liberty.services.UserTradeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.collect.Iterables.isEmpty;
import static com.liberty.common.ComparatorUtils.foundMinList;

/**
 * @author Dmytro_Kovalskyi.
 * @since 30.01.2017.
 */
@Service
@Slf4j
public class BuyPlayerServiceImpl implements BuyPlayerService {

    @Autowired
    private UserTradeConfigService tradeConfigService;

    @Autowired
    private RequestService requestService;

    @Override
    public void runBuyProcedure() {
        Iterable<UserTradeConfig> configs = tradeConfigService.getActiveUserConfigs();
        if (configs == null) {
            log.info("There are no active users");
            return;
        }
        Lists.newArrayList(configs).parallelStream().forEach(this::checkPlayers);
    }

    private void checkPlayers(UserTradeConfig userTradeConfig) {

        List<PlayerTradeStatus> players = userTradeConfig.getPlayers().values().stream()
                .filter(filterPlayersToAutoBuy())
                .collect(Collectors.toList());
        Collections.shuffle(players, new Random(System.currentTimeMillis()));
        log.info("Monitor : " + players.size() + " players" + ". For " + userTradeConfig.getUserId());
        if (isEmpty(players)) {
            log.info("Nothing to buy. Player trade is empty. For " + userTradeConfig.getUserId());
            return;
        }
        for (PlayerTradeStatus p : players) {
            log.info("Trying to check " + p.getPlayerId() + " max price => " + p.getBuyPrice());

            boolean success = checkMarket(p);
            if (!success) {
                log.error("Error player buy operation");
            }
            DelayHelper.wait(2000, 200);          // TODO: create priority queues for requests per user
        }

    }

    private Predicate<PlayerTradeStatus> filterPlayersToAutoBuy() {
        return PlayerTradeStatus::isEnabled;
    }

    private boolean checkMarket(PlayerTradeStatus playerTradeStatus) {
        try {
            PlayerId playerId = playerTradeStatus.getPlayerId();
            Optional<TradeStatus> maybe = requestService.searchPlayer(playerId.getId(), playerId.getRareFlag(),
                    playerTradeStatus.getBuyPrice());
            if (!maybe.isPresent()) {
                return false;
            }
            TradeStatus tradeStatus = maybe.get();
            int found = tradeStatus.getAuctionInfo().size();
            log.info("Found " + found + " players for " + playerId + " maxPrice : " + playerTradeStatus.getBuyPrice());
            if (found <= 0) {
                return true;
            }
            buyPlayers(tradeStatus, playerTradeStatus);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private void buyPlayers(TradeStatus tradeStatus, PlayerTradeStatus playerTradeStatus) {

        List<AuctionInfo> list = foundMinList(tradeStatus);
        if (list.isEmpty()) {
            log.error("Can not find trades for " + playerTradeStatus.getPlayerId());
            return;
        }  // TODO: check max purchases
        for (AuctionInfo info : list) {
            boolean success = buyOne(info, playerTradeStatus);
            if (!success) {
                return;
            }
        }
    }

    private boolean buyOne(AuctionInfo auctionInfo, PlayerTradeStatus playerTradeStatus) {
        log.info("Found min player : " + auctionInfo.getBuyNowPrice());
        boolean success = requestService.buy(auctionInfo);
        if (!success) {
            log.error("Can not buy " + auctionInfo.getTradeId() + " trade");
            return false;
        }
        log.info("Success bought " + playerTradeStatus.getPlayerId() + " for " + auctionInfo.getBuyNowPrice());

        return true;
    }


}
