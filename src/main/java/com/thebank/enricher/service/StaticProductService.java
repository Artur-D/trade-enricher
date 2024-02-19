package com.thebank.enricher.service;

import com.thebank.enricher.model.dto.CsvProduct;
import io.reactivex.Observable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@EnableAsync
@Service
class StaticProductService implements ProductService {

    @Value("${products.csv.path}")
    private Resource productsCsvResource;

    @Autowired
    private CsvParserService csvParserService;
    private Map<Long, CsvProduct> productIdToProduct = null;

    @PostConstruct
    private void loadProducts() {
        try (InputStreamReader reader = new InputStreamReader(productsCsvResource.getInputStream())) {
            csvParserService.parseProducts(reader)
                    .subscribe(
                            products -> {
                                productIdToProduct = Collections.unmodifiableMap(
                                        products.stream()
                                                .collect(Collectors.toMap(CsvProduct::getProductId, product -> product))
                                );
                            });

        } catch (Exception e) {
            throw new IllegalStateException("Products CSV parsing went wrong:", e);
        }

    }


    @Override
    public Observable<Map<Long, CsvProduct>> provideProducts() {
        return Observable.just(productIdToProduct);
    }

    /**
     * Provides product name by productId
     * @param productId
     * @return productName OR NULL if the product has not been found
     */
    @Override
    public String provideProductNameById(Long productId) {
        CsvProduct csvProduct = productIdToProduct.get(productId);

        if (csvProduct != null) {
            return csvProduct.getProductName();
        }

        return null;
    }
}
