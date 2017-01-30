package com.liberty.common;

import com.liberty.model.AuctionInfo;
import com.liberty.model.TradeStatus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmytro_Kovalskyi.
 * @since 30.01.2017.
 */
public class ComparatorUtils {

    public static Comparator<AuctionInfo> getAuctionInfoComparator() {
        return (a1, a2) -> {
            int contract1 = a1.getItemData().getContract();
            int contract2 = a2.getItemData().getContract();
            if (contract1 <= 0 && contract2 <= 0) {
                return 0;
            }
            if (contract1 <= 0 && contract2 > 0) {
                return 1;
            }
            if (contract1 > 0 && contract2 <= 0) {
                return -1;
            }
            return a1.getBuyNowPrice().compareTo(a2.getBuyNowPrice());
        };
    }

    public static List<AuctionInfo> foundMinList(TradeStatus tradeStatus) {
        return tradeStatus.getAuctionInfo().stream()
                .filter(a -> a.getItemData().getContract() > 0)
                .sorted(getAuctionInfoComparator().reversed())
                .collect(Collectors.toList());
    }
}
