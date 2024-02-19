package com.thebank.enricher.service;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.thebank.enricher.model.dto.CsvProduct;
import com.thebank.enricher.model.dto.CsvTradeDto;
import io.reactivex.Observable;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;

@Service
class SimpleCsvParserService implements CsvParserService {

    @Override
    public Observable<Collection<CsvTradeDto>> parseTrades(Reader tradesReader) {

        List<CsvTradeDto> parsedCsvTrades = getCsvToBeanBuilder(tradesReader, CsvTradeDto.class)
                .parse();

        return Observable.just(parsedCsvTrades);
    }

    @Override
    public Observable<Collection<CsvProduct>> parseProducts(Reader productsReader) {

        List<CsvProduct> parsedCsvTrades = getCsvToBeanBuilder(productsReader, CsvProduct.class)
                .parse();

        return Observable.just(parsedCsvTrades);
    }

    @Override
    public String parseToCsvString(Collection dtos) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        StringWriter writer = new StringWriter();
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withApplyQuotesToAll(false)
                .build();
        beanToCsv.write(dtos.iterator());

        return writer.toString();
    }

    private CsvToBean getCsvToBeanBuilder(Reader reader, Class type) {


        return new CsvToBeanBuilder(reader)
                .withType(type)
                .build();
    }
}
