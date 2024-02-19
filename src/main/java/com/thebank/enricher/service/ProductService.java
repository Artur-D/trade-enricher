package com.thebank.enricher.service;

import com.thebank.enricher.model.dto.CsvProduct;
import io.reactivex.Observable;

import java.util.Map;

public interface ProductService {
    /**
     * Provides a map of productId -> CsvProduct
     * The implementation should be cached/lazy loaded
     * @return a map of productId -> CsvProduct
     */
    Observable<Map<Long, CsvProduct>> provideProducts();

    /**
     * Provides productName for its ID
     * The implementation should return a default value if the name is not found
     * @param productId
     * @return product name or a default value
     */
    String provideProductNameById(Long productId);
}
