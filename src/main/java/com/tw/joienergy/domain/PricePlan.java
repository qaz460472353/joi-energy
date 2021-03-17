package com.tw.joienergy.domain;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Description 价格计划
 * @Author ZY
 * @Date 2021/3/3 17:36
 **/
public class PricePlan {
    /**
     * 电力供应商
     */
    private final String energySupplier;
    /**
     * 计划名称
     */
    private final String planName;
    /**
     * 价格单位 unit price per kWh
     */
    private final BigDecimal unitRate;
    /**
     * 高峰时段列表
     */
    private final List<PeakTimeMultiplier> peakTimeMultipliers;

    public PricePlan(String energySupplier, String planName, BigDecimal unitRate, List<PeakTimeMultiplier> peakTimeMultipliers) {
        this.energySupplier = energySupplier;
        this.planName = planName;
        this.unitRate = unitRate;
        this.peakTimeMultipliers = peakTimeMultipliers;
    }

    public String getEnergySupplier() {
        return energySupplier;
    }

    public String getPlanName() {
        return planName;
    }

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public BigDecimal getPrice (LocalDateTime dateTime) {
        return peakTimeMultipliers.stream()
                .filter(peakTimeMultiplier -> peakTimeMultiplier.dayOfWeek.equals(dateTime.getDayOfWeek()))
                .findFirst()
                .map(peakTimeMultiplier -> unitRate.multiply(peakTimeMultiplier.bigDecimal))
                .orElse(unitRate);
    }

    static class PeakTimeMultiplier {
        DayOfWeek dayOfWeek;
        BigDecimal bigDecimal;

        public PeakTimeMultiplier(DayOfWeek dayOfWeek, BigDecimal bigDecimal) {
            this.dayOfWeek = dayOfWeek;
            this.bigDecimal = bigDecimal;
        }
    }
}
