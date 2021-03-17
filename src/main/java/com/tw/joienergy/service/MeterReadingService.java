package com.tw.joienergy.service;

import com.tw.joienergy.domain.ElectricityReading;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description TODO
 * @Author ZY
 * @Date 2021/3/4 10:26
 **/
@Service
public class MeterReadingService {

    private final Map<String, List<ElectricityReading>> meterAssociatedReadings;

    public MeterReadingService(Map<String, List<ElectricityReading>> meterAssociatedReadings) {
        this.meterAssociatedReadings = meterAssociatedReadings;
    }

    /**
     * 根据电表ID读取信息
     * @param smartMeterId 电表ID
     * @return
     */
    public Optional<List<ElectricityReading>> getReadings(String smartMeterId) {
        return Optional.ofNullable(meterAssociatedReadings.get(smartMeterId));
    }

    /**
     * 根据电表ID读取信息
     * 时间条件：上周
     * @param smartMerId
     * @return
     */
    public Optional<List<ElectricityReading>> getReadingsLimtTime(String smartMerId) {
        List<ElectricityReading> electricityReadings = meterAssociatedReadings.get(smartMerId);
        ArrayList<ElectricityReading> lastWeekElectricityReading = new ArrayList<>();
        // totalEnergy
        Long totalEnergy = null;
        Long totalTime = null;

        // lastweek 限制
        long currentTime = System.currentTimeMillis();
        long lastWeekTime = currentTime - (7 * 24 * 3600);
        for (ElectricityReading electricityReading : electricityReadings) {
            Instant time = electricityReading.getTime();
            if (time.isBefore(Instant.ofEpochMilli(currentTime)) && time.isAfter(Instant.ofEpochMilli(lastWeekTime))) {
                lastWeekElectricityReading.add(electricityReading);
                totalEnergy += electricityReading.getReading().longValue();
                totalTime += electricityReading.getTime().toEpochMilli();
            }
        }
        int size = electricityReadings.size();
        long averageEnergy = totalEnergy / size;
        long totalAverageEnergy = averageEnergy * (totalTime / 3600);


        return null;
    }

    /**
     * 存储读取到的耗电信息
     * @param smartMeterId 智能电表ID
     * @param electricityReadings 耗电量的集合
     */
    public void storeReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
        if (!meterAssociatedReadings.containsKey(smartMeterId)) {
            meterAssociatedReadings.put(smartMeterId, new ArrayList<>());
        }
        meterAssociatedReadings.get(smartMeterId).addAll(electricityReadings);
    }
}
