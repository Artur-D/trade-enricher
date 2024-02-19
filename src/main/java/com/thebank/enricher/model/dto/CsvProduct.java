package com.thebank.enricher.model.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CsvProduct {
    @CsvBindByName(column = "product_id")
    private Long productId;

    @CsvBindByName(column = "product_name")
    private String productName;

}