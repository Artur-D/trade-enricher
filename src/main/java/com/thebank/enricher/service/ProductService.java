package com.thebank.enricher.service;

import com.thebank.enricher.model.dto.CsvProduct;
import io.reactivex.Observable;

import java.util.Map;

public interface ProductService {
    Observable<Map<Long, CsvProduct>> provideProducts();

    String provideProductNameById(Long productId);
}
