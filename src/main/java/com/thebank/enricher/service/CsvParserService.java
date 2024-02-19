package com.thebank.enricher.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.thebank.enricher.model.dto.CsvProduct;
import com.thebank.enricher.model.dto.CsvTradeDto;
import io.reactivex.Observable;

import java.io.Reader;
import java.util.Collection;

public interface CsvParserService {

    Observable<Collection<CsvTradeDto>> parseTrades(Reader tradesReader);

    Observable<Collection<CsvProduct>> parseProducts(Reader productsReader);

    /**
     * A non-typed version of parsing many dtos into single String
     * @param dtos dtos that will be parsed to CSV
     * @return CSV content as a String
     * @throws CsvDataTypeMismatchException
     * @throws CsvRequiredFieldEmptyException
     */
    String parseToCsvString(Collection dtos) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
}
