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

    String parseToCsvString(Collection dtos) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
}
