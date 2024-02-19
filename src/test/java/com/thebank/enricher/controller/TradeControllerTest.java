package com.thebank.enricher.controller;

import com.thebank.enricher.model.dto.CsvTradeDto;
import com.thebank.enricher.model.dto.EnrichedTradeDto;
import com.thebank.enricher.service.CsvParserService;
import com.thebank.enricher.service.TradeEnrichmentService;
import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//
//@WebMvcTest(TradeController.class)
//class TradeControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TradeEnrichmentService tradeEnrichmentService;
//
//    @Test
//    void testEnrichTradesEndpoint() throws Exception {
//        mockMvc.perform(post("/api/trades/enrich")
//                        .param("file", "fileContent"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].productName").value("ProductNameForP1"));
//    }


@ExtendWith(MockitoExtension.class)
class TradeControllerTest {

    @Mock
    private TradeEnrichmentService tradeEnrichmentService;

    @Mock
    private CsvParserService csvParserService;

    @InjectMocks
    private TradeController tradeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    void enrichTradesTest() throws Exception {
        String inputCsv = "date,product_id,currency,price\n20200101,1,USD,100.0";
        String expectedCsv = "date,product_name,currency,price\n20200101,Treasury Bills,USD,100.0";

        // Mocking the service layer behavior
        when(csvParserService.parseTrades(any(Reader.class))).thenReturn(Observable.just(List.of(getCsvTradeDto()))); // Replace Trade with your actual trade DTO
        when(tradeEnrichmentService.enrichTrades(anyCollection())).thenReturn(Observable.just(List.of(new EnrichedTradeDto(null, null, null, null)))); // Replace EnrichedTrade with your actual enriched trade DTO
        when(csvParserService.parseToCsvString(anyCollection())).thenReturn(expectedCsv);

        // Perform the test
        mockMvc.perform(post(BaseController.TRADE_PREFIX + "/enrich")
                        .contentType("text/csv")
                        .content(inputCsv))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andExpect(content().string(expectedCsv));
    }

    private CsvTradeDto getCsvTradeDto() {

        return new CsvTradeDto("20160101", 1L, "EUR", BigDecimal.valueOf(10.0));
    }
}
