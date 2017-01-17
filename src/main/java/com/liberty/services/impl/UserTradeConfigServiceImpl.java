package com.liberty.services.impl;

import com.liberty.model.PlayerId;
import com.liberty.model.PlayerTradeStatus;
import com.liberty.model.User;
import com.liberty.model.UserTradeConfig;
import com.liberty.repositories.UserTradeConfigRepository;
import com.liberty.services.UserService;
import com.liberty.services.UserTradeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Service
@Slf4j
public class UserTradeConfigServiceImpl implements UserTradeConfigService {

    private UserTradeConfigRepository configRepository;
    private UserService userService;

    @Override
    public void addPlayerToAutoBuy(PlayerTradeStatus tradeStatus) {
        UserTradeConfig config = getCurrentUserConfig();
        config.addPlayer(tradeStatus);
        configRepository.save(config);
    }

    @Override
    public void deletePlayerToAutoBuy(PlayerId playerId) {
        UserTradeConfig config = getCurrentUserConfig();
        PlayerTradeStatus removed = config.getPlayers().remove(playerId);
        if (removed == null) {
            log.error("Player with id : " + playerId + " not found");
        } else {
            configRepository.save(config);
        }
    }

    @Override
    public void updatePlayer(PlayerTradeStatus request) {
        UserTradeConfig config = getCurrentUserConfig();

        PlayerTradeStatus tradeStatus = config.getPlayers().get(request.getPlayerId());
        if (tradeStatus == null) {
            log.error("Player with id : " + request.getPlayerId() + " not found");
            return;
        }

        config.getPlayers().put(request.getPlayerId(), request);
        configRepository.save(config);
    }

    private UserTradeConfig getCurrentUserConfig() {
        User user = userService.getCurrentUser();
        UserTradeConfig config = configRepository.findOne(user.getStringId());
        if (config == null) {
            config = createNewTradeConfig(user);
        }
        return config;
    }

    private UserTradeConfig createNewTradeConfig(User user) {
        UserTradeConfig tradeConfig = new UserTradeConfig();
        tradeConfig.setUserId(user.getStringId());
        return tradeConfig;
    }

    @Override
    public void disableAll() {
        UserTradeConfig config = getCurrentUserConfig();
        config.getPlayers().values().forEach(x -> {
            x.setEnabled(false);
        });
        configRepository.save(config);
    }

    @Override
    public void enablePlayer(PlayerId playerId, boolean enabled) {
        UserTradeConfig config = getCurrentUserConfig();
        PlayerTradeStatus tradeStatus = config.getPlayers().get(playerId);
        if (tradeStatus == null) {
            log.error("Player with id : " + playerId + " not found");
            return;
        }
        tradeStatus.setEnabled(enabled);
        configRepository.save(config);
    }

    @Override
    public Map<PlayerId, PlayerTradeStatus> getAllTrades() {
        return getCurrentUserConfig().getPlayers();
    }
}
