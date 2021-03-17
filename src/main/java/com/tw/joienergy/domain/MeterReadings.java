package com.tw.joienergy.domain;

import java.util.List;

/**
 * @Description 电表读数
 * @Author ZY
 * @Date 2021/3/3 17:50
 **/
public class MeterReadings {
    /**
     * 电表读数集合
     */
    private List<ElectricityReading> electricityReadings;
    /**
     * 智能电表ID
     */
    private String smartMeterId;

    public MeterReadings() {}
    /**
     * 电表信息读取
     * @param smartMeterId  智能电表ID
     * @param electricityReadings 该电表的耗电记录集合
     */
    public MeterReadings(String smartMeterId,List<ElectricityReading> electricityReadings) {
        this.electricityReadings = electricityReadings;
        this.smartMeterId = smartMeterId;
    }

    /**
     * 获取电表的耗电记录集合
     * @return 耗电记录集合
     */
    public List<ElectricityReading> getElectricityReadings() {
        return electricityReadings;
    }

    /**
     * 智能设备ID
     * @return 智能设备ID
     */
    public String getSmartMeterId() {
        return smartMeterId;
    }
}
