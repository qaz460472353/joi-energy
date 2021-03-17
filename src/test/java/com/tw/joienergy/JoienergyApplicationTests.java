package com.tw.joienergy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.joienergy.builders.MeterReadingsBuilder;
import com.tw.joienergy.domain.MeterReadings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JoienergyApplication.class)
class JoienergyApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private TestRestTemplate restTemplate;
    @Qualifier("objectMapper")
    @Autowired
    private ObjectMapper mapper;

    /**
     * 存储读表信息
     * @throws JsonProcessingException
     */
    @Test
    public void shouldStoreReadings() throws JsonProcessingException {
        MeterReadings meterReadings = new MeterReadingsBuilder().generateElectricityReadings().build();
        HttpEntity<String> entity = getStringHttpEntity(meterReadings);

        ResponseEntity<String> response = restTemplate.postForEntity("/readings/store", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * 给定仪表ID返回仪表读取信息
     * @throws JsonProcessingException
     */
    @Test
    public void givenMeterIdShouldReturnAMeterReadingAssociatedWithMeterId() throws JsonProcessingException {
        String smartMeterId = "bob";
        populateMeterReadingsForMeter(smartMeterId);

        ResponseEntity<String> response = restTemplate.getForEntity("/readings/read/" + smartMeterId, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * 计算出所有的价格
     * @throws JsonProcessingException
     */
    @Test
    public void shouldCalculateAllPrices() throws JsonProcessingException {
        String smartMeterId = "bob";
        populateMeterReadingsForMeter(smartMeterId);

        ResponseEntity<String> response = restTemplate.getForEntity("/price-plans/compare-all/" + smartMeterId, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * 推荐两条最便宜的价格计划
     * @throws JsonProcessingException
     */
    @Test
    public void givenMeterIdAndLimitShouldReturnRecommendedCheapestPricePlans() throws JsonProcessingException {
        String smartMeterId = "bob";
        populateMeterReadingsForMeter(smartMeterId);

        ResponseEntity<String> response =
                restTemplate.getForEntity("/price-plans/recommend/" + smartMeterId + "?limit=2", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * 封装请求参数
     * @param object 请求参数对象
     * @return 封装后请求参数对象
     * @throws JsonProcessingException
     */
    private HttpEntity<String> getStringHttpEntity(Object object) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonMeterData = mapper.writeValueAsString(object);
        return (HttpEntity<String>) new HttpEntity(jsonMeterData, headers);
    }

    /**
     * 填充仪表读数
     * @param smartMeterId 智能仪表ID
     * @throws JsonProcessingException
     */
    private void populateMeterReadingsForMeter(String smartMeterId) throws JsonProcessingException {
        MeterReadings readings = new MeterReadingsBuilder().setSmartMeterId(smartMeterId)
                .generateElectricityReadings(20)
                .build();

        HttpEntity<String> entity = getStringHttpEntity(readings);
        restTemplate.postForEntity("/readings/store", entity, String.class);
    }
}
