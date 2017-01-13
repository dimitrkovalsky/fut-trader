package com.liberty.services.impl;

import com.liberty.model.PlayerTradeStatus;
import com.liberty.model.User;
import com.liberty.model.UserTradeConfig;
import com.liberty.repositories.UserTradeConfigRepository;
import com.liberty.services.UserService;
import com.liberty.services.UserTradeConfigService;
import org.springframework.stereotype.Service;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Service
public class UserTradeConfigServiceImpl implements UserTradeConfigService {

    private UserTradeConfigRepository configRepository;
    private UserService userService;

    public void addPlayerToAutoBuy(PlayerTradeStatus tradeStatus) {
        UserTradeConfig config = getCurrentUserConfig();
        config.addPlayer(tradeStatus);
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

    public void disableAll() {
        UserTradeConfig config = getCurrentUserConfig();
        config.getPlayers().forEach(x -> {
            x.setEnabled(false);
        });
        configRepository.save(config);
    }

    // TODO: use map [playerId, rareflag] -> PLayerTradeStatus
    public void enablePlayer(Long playerId, boolean enabled) {
//        PlayerTradeStatus status = tradeRepository.findOne(id);
//        if (status == null) {
//            logController.error("Can not find player for " + id);
//            return;
//        }
//        status.setEnabled(true);
//        status.setMaxPrice(maxPrice);
//        status.addTag(tag);
//        tradeRepository.save(status);
    }
}
