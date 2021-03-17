package com.tw.joienergy.domain;

import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

public class PricePlanTest {

    private final String ENERGY_SUPPLIER_NAME = "Energy Supplier Name";

    /**
     * 返回构造函数中给出的能源供应商
     */
    @Test
    public void shouldReturnTheEnergySupplierGivenInTheConstructor() {
        PricePlan pricePlan = new PricePlan(null, ENERGY_SUPPLIER_NAME, null, null);

        AssertionsForClassTypes.assertThat(pricePlan.getEnergySupplier()).isEqualTo(ENERGY_SUPPLIER_NAME);
    }

    /**
     * 返回给定普通日期时间的基本价格
     * @throws Exception
     */
    @Test
    public void shouldReturnTheBasePriceGivenAnOrdinaryDateTime() throws Exception {
        LocalDateTime normalDateTime = LocalDateTime.of(2017, Month.AUGUST, 31, 12, 0, 0);
        PricePlan.PeakTimeMultiplier peakTimeMultiplier = new PricePlan.PeakTimeMultiplier(DayOfWeek.WEDNESDAY, BigDecimal.TEN);
        PricePlan pricePlan = new PricePlan(null, null, BigDecimal.ONE, singletonList(peakTimeMultiplier));

        BigDecimal price = pricePlan.getPrice(normalDateTime);

        AssertionsForClassTypes.assertThat(price).isCloseTo(BigDecimal.ONE, Percentage.withPercentage(1));
    }

    /**
     * 返回特殊日期时间的特殊价格
     * @throws Exception
     */
    @Test
    public void shouldReturnAnExceptionPriceGivenExceptionalDateTime() throws Exception {
        LocalDateTime exceptionalDateTime = LocalDateTime.of(2017, Month.AUGUST, 30, 23, 0, 0);
        PricePlan.PeakTimeMultiplier peakTimeMultiplier = new PricePlan.PeakTimeMultiplier(DayOfWeek.WEDNESDAY, BigDecimal.TEN);
        PricePlan pricePlan = new PricePlan(null, null, BigDecimal.ONE, singletonList(peakTimeMultiplier));

        BigDecimal price = pricePlan.getPrice(exceptionalDateTime);

        AssertionsForClassTypes.assertThat(price).isCloseTo(BigDecimal.TEN, Percentage.withPercentage(1));
    }

    /**
     * 接收到多个特殊的日期时间
     * @throws Exception
     */
    @Test
    public void shouldReceiveMultipleExceptionalDateTimes() throws Exception {
        LocalDateTime exceptionalDateTime = LocalDateTime.of(2017, Month.AUGUST, 30, 23, 0, 0);
        PricePlan.PeakTimeMultiplier peakTimeMultiplier = new PricePlan.PeakTimeMultiplier(DayOfWeek.WEDNESDAY, BigDecimal.TEN);
        PricePlan.PeakTimeMultiplier otherPeakTimeMultiplier = new PricePlan.PeakTimeMultiplier(DayOfWeek.TUESDAY, BigDecimal.TEN);
        List<PricePlan.PeakTimeMultiplier> peakTimeMultipliers = Arrays.asList(peakTimeMultiplier, otherPeakTimeMultiplier);
        PricePlan pricePlan = new PricePlan(null, null, BigDecimal.ONE, peakTimeMultipliers);

        BigDecimal price = pricePlan.getPrice(exceptionalDateTime);

        AssertionsForClassTypes.assertThat(price).isCloseTo(BigDecimal.TEN, Percentage.withPercentage(1));
    }
}
