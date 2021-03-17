package com.tw.joienergy.domain;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @Description 耗电量读取表
 * @Author ZY
 * @Date 2021/3/3 17:37
 **/
public class ElectricityReading {
    /**
     * 时代时间戳记
     */
    private Instant time;
    /**
     * 电表读数 kW
     */
    private BigDecimal reading;

    public ElectricityReading() { }

    public ElectricityReading(Instant time, BigDecimal reading) {
        this.time = time;
        this.reading = reading;
    }

    public BigDecimal getReading() {
        return reading;
    }

    public Instant getTime() {
        return time;
    }
}
