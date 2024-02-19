package com.thebank.enricher;

import com.thebank.enricher.controller.BaseController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TradeControllerFullStackTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testEnrichTrades() throws Exception {
        // given
        ClassPathResource resource = new ClassPathResource("trade.csv");
        String tradesCsvStringBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        String expectedResponse = """
                CURRENCY,DATE,PRICE,PRODUCT_NAME
                EUR,20160101,10.0,Treasury Bills Domestic
                EUR,20160101,20.1,Corporate Bonds Domestic
                EUR,20160101,30.34,REPO Domestic
                EUR,20160101,35.34,Missing Product Name
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "text/csv");
        headers.set("Accept", "text/csv");


        HttpEntity<String> requestEntity = new HttpEntity<>(tradesCsvStringBody, headers);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + BaseController.TRADE_PREFIX + "/enrich", requestEntity, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        assertThat(response.getHeaders().getContentType().toString()).isEqualTo("text/csv;charset=UTF-8");
    }
}
