package com.thebank.enricher.model.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CsvTradeDto{
    @CsvBindByName(column = "date")
    private String date;

    @CsvBindByName(column = "product_id")
    private Long productId;

    @CsvBindByName(column = "currency")
    private String currency;

    @CsvBindByName(column = "price")
    private BigDecimal price;

}