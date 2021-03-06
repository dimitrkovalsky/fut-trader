package com.liberty.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TradeStatus {

    private Object errorState;
    private Integer credits;
    private List<AuctionInfo> auctionInfo;
    private Object duplicateItemIdList;
    private Object bidTokens;
    private ArrayList<Currency> currencies;
    private Object debug;
    private String string;
    private Integer code;
    private String reason;
}
