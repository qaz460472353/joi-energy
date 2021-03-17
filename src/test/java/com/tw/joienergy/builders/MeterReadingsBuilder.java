package com.tw.joienergy.builders;

import com.tw.joienergy.domain.ElectricityReading;
import com.tw.joienergy.domain.MeterReadings;
import com.tw.joienergy.generator.ElectricityReadingsGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author ZY
 * @Date 2021/3/4 10:37
 **/
public class MeterReadingsBuilder {

    private static final String DEFAULT_METER_ID = "id";

    private String smartMeterId = DEFAULT_METER_ID;
    private List<ElectricityReading> electricityReadings = new ArrayList<>();

    public MeterReadingsBuilder setSmartMeterId(String smartMeterId) {
        this.smartMeterId = smartMeterId;
        return this;
    }

    public MeterReadingsBuilder generateElectricityReadings() {
        return generateElectricityReadings(5);
    }

    /**
     * 产生耗电量读数
     * @param number 耗电量读数
     * @return 仪表读数对象
     */
    public MeterReadingsBuilder generateElectricityReadings(int number) {
        ElectricityReadingsGenerator readingsBuilder = new ElectricityReadingsGenerator();
        this.electricityReadings = readingsBuilder.generate(number);
        return this;
    }

    public MeterReadings build() {
        return new MeterReadings(smartMeterId, electricityReadings);
    }
}
