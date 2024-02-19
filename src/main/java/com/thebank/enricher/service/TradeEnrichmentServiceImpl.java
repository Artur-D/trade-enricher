package com.thebank.enricher.service;

import com.thebank.enricher.model.dto.CsvTradeDto;
import com.thebank.enricher.model.dto.EnrichedTradeDto;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
class TradeEnrichmentServiceImpl implements TradeEnrichmentService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final ProductService productService;
    private final String defaultProductName;

    TradeEnrichmentServiceImpl(ProductService productService,
                               @Value("${products.default.name}") String defaultProductName) {
        this.productService = productService;
        this.defaultProductName = defaultProductName;
    }

    @Override
    public Observable<Collection<EnrichedTradeDto>> enrichTrades(Collection<CsvTradeDto> trades) {

        List<EnrichedTradeDto> enrichedTrades = trades.stream()
                .filter(trade -> isValidDate(trade.getDate()))
                .map(trade -> {
                    String productNameById = productService.provideProductNameById(trade.getProductId());
                    if (StringUtils.isBlank(productNameById)) {
                        log.warn("Product name empty for a productId={}", trade.getProductId());
                        productNameById = defaultProductName;
                    }
                    return new EnrichedTradeDto(trade.getDate(), productNameById, trade.getCurrency(), trade.getPrice());

                })
                .collect(Collectors.toList());

        return Observable.just(enrichedTrades);
    }


    /**
     * Validates if the given date string is in the YYYYMMDD format.
     *
     * @param dateString The date string to validate.
     * @return true if the date is valid; false otherwise.
     */
    private static boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            log.error("Invalid date format for '{}'. Expected format is YYYYMMDD.", dateString);
            return false;
        }
    }
}
