package com.tw.joienergy.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description TODO
 * @Author ZY
 * @Date 2021/3/4 10:34
 **/
@Service
public class AccountService {
    private final Map<String, String> smartMeterToPricePlanAccounts;

    public AccountService(Map<String, String> smartMeterToPricePlanAccounts) {
        this.smartMeterToPricePlanAccounts = smartMeterToPricePlanAccounts;
    }

    /**
     * 根据智能电表ID获取价格计划ID
     * @param smartMeterId 智能电表ID
     * @return 价格计划ID
     */
    public String getPricePlanIdForSmartMeterId(String smartMeterId) {
        return smartMeterToPricePlanAccounts.get(smartMeterId);
    }
}
