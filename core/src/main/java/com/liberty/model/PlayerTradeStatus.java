package com.liberty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerTradeStatus {

    private PlayerId playerId;

    private Integer buyPrice;
    private Integer sellStartPrice;
    private Integer sellBuyNowPrice;

    private boolean enabled;

}
