package com.thebank.enricher.model.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrichedTradeDto {

    @CsvBindByName(column = "date")
    private String date;

    @CsvBindByName(column = "product_name")
    private String productName;

    @CsvBindByName(column = "currency")
    private String currency;

    @CsvBindByName(column = "price")
    private BigDecimal price;
}

