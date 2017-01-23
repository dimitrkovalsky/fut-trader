package com.liberty.rest;

import com.liberty.common.StringConstants;
import com.liberty.model.PlayerId;
import com.liberty.model.PlayerTradeStatus;
import com.liberty.rest.request.AutoBuyRequest;
import com.liberty.services.UserTradeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Dmytro_Kovalskyi.
 * @since 17.01.2017.
 */
@RestController
@RequestMapping("/api/trade/config")
@PreAuthorize(StringConstants.SECURED_ROLES_EXPRESSION)
public class TradeConfigService {

    @Autowired
    private UserTradeConfigService tradeConfigService;


    @PreAuthorize(StringConstants.SECURED_ROLES_EXPRESSION)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void removePlayer(@PathVariable PlayerId id) {
        tradeConfigService.deletePlayerToAutoBuy(id);
    }

    @PreAuthorize(StringConstants.SECURED_ROLES_EXPRESSION)
    @RequestMapping(method = RequestMethod.GET)
    public Map<PlayerId, PlayerTradeStatus> getAll() {
        return  tradeConfigService.getAllTrades();
    }


    @PreAuthorize(StringConstants.SECURED_ROLES_EXPRESSION)
    @RequestMapping(path = "/autobuy/player", method = RequestMethod.POST)
    public void updateAutoBuy(@RequestBody AutoBuyRequest request) {
        tradeConfigService.enablePlayer(request.getPlayerId(), request.getEnabled());
    }

    @PreAuthorize(StringConstants.SECURED_ROLES_EXPRESSION)
    @RequestMapping(path = "/player/update", method = RequestMethod.POST)
    public void updatePlayer(@RequestBody PlayerTradeStatus request) {
        tradeConfigService.addPlayerToAutoBuy(request);
    }
}
