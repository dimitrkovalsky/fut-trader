package com.liberty.services.impl;

import com.liberty.model.PlayerId;
import com.liberty.model.PlayerTradeStatus;
import com.liberty.model.User;
import com.liberty.model.UserTradeConfig;
import com.liberty.repositories.UserParameterRepository;
import com.liberty.repositories.UserTradeConfigRepository;
import com.liberty.services.UserService;
import com.liberty.services.UserTradeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Service
@Slf4j
public class UserTradeConfigServiceImpl implements UserTradeConfigService {

    @Autowired
    private UserTradeConfigRepository configRepository;

    @Autowired
    private UserParameterRepository parameterRepository;

    @Autowired
    private UserService userService;

    @Override
    public void addPlayerToAutoBuy(PlayerTradeStatus tradeStatus) {
        UserTradeConfig config = getCurrentUserConfig();
        config.addPlayer(tradeStatus);    // TODO: check price limits
        configRepository.save(config);
    }

    @Override
    public void deletePlayerToAutoBuy(PlayerId playerId) {
        UserTradeConfig config = getCurrentUserConfig();
        PlayerTradeStatus removed = config.remove(playerId);
        if (removed == null) {
            log.error("Player with id : " + playerId + " not found");
        } else {
            configRepository.save(config);
        }
    }

    @Override
    public void updatePlayer(PlayerTradeStatus request) {
        UserTradeConfig config = getCurrentUserConfig();

        PlayerTradeStatus tradeStatus = config.getPlayer(request.getPlayerId());
        if (tradeStatus == null) {
            log.error("Player with id : " + request.getPlayerId() + " not found");
            return;
        }

        config.addPlayer(request);
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
    public Iterable<UserTradeConfig> getActiveUserConfigs() {
        List<String> ids = parameterRepository.findAllByAutoBuyEnabled(true)
                .stream().map(x -> x.getUserId().toString())
                .collect(Collectors.toList());
        return configRepository.findAll(ids);
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
        PlayerTradeStatus tradeStatus = config.getPlayer(playerId);
        if (tradeStatus == null) {
            log.error("Player with id : " + playerId + " not found");
            return;
        }
        tradeStatus.setEnabled(enabled);
        configRepository.save(config);
    }

    @Override
    public UserTradeConfig getAllTrades() {
        return getCurrentUserConfig();
    }
}
