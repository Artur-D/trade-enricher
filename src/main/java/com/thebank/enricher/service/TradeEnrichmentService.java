package com.thebank.enricher.service;

import com.thebank.enricher.model.dto.EnrichedTradeDto;
import com.thebank.enricher.model.dto.CsvTradeDto;
import io.reactivex.Observable;

import java.util.Collection;

public interface TradeEnrichmentService {

    /**
     * Enrich trade data with product names etc.
     * @param trades trades dto data
     * @return Collection<EnrichedTradeDto> enriched data with product names
     */
    Observable<Collection<EnrichedTradeDto>> enrichTrades(Collection<CsvTradeDto> trades);
}
