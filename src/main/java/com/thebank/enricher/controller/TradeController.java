package com.thebank.enricher.controller;

import com.thebank.enricher.service.CsvParserService;
import com.thebank.enricher.service.TradeEnrichmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.io.StringReader;

@RestController
@RequestMapping(BaseController.TRADE_PREFIX)
public class TradeController extends BaseController {

    private final TradeEnrichmentService tradeEnrichmentService;
    private final CsvParserService csvParserService;

    TradeController(TradeEnrichmentService tradeEnrichmentService, CsvParserService csvParserService) {
        this.tradeEnrichmentService = tradeEnrichmentService;
        this.csvParserService = csvParserService;
    }

    /**
     * @param tradesCsvString
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/enrich", consumes = "text/csv", produces = "text/csv")
    public DeferredResult<String> enrichTrades(@RequestBody String tradesCsvString) throws IOException {


        StringReader tradesCsvStringReader = new StringReader(tradesCsvString);

        return deferredObservable(
                csvParserService.parseTrades(tradesCsvStringReader)
                        .flatMap(tr -> tradeEnrichmentService.enrichTrades(tr))
                        .map(enrichedTrades -> {
                            return csvParserService.parseToCsvString(enrichedTrades);
                        })
        );


    }


}
