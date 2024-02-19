package com.thebank.enricher.service;

import com.thebank.enricher.model.dto.CsvProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {StaticProductService.class})
class StaticProductServiceTest {

    @Autowired
    private StaticProductService staticProductService;

    @Value("classpath:products.csv")
    private Resource productsCsvResource;

    @Test
    void testProvideProducts() throws IOException {
        // Trigger the loading of products manually in the test context
//        staticProductService.loadProducts();

        // Test the provideProducts method
        Map<Long, CsvProduct> products = staticProductService.provideProducts().blockingFirst();
        assertNotNull(products);
        // Assuming you know the size of your test CSV file
        assertEquals(2, products.size()); // Replace '2' with the actual number of products in your CSV
    }

    @Test
    void testProvideProductNameById() throws IOException {
        // Trigger the loading of products manually in the test context
//        staticProductService.loadProducts();

        // Test provideProductNameById with an ID you know exists in the CSV
        String productName = staticProductService.provideProductNameById(1L); // Replace '1L' with a valid product ID
        assertNotNull(productName);
        assertEquals("Expected Product Name", productName); // Replace 'Expected Product Name' with the actual product name

        // Test provideProductNameById with a non-existing ID
        String nonExistingProductName = staticProductService.provideProductNameById(999L); // Use an ID that doesn't exist
        assertNull(nonExistingProductName);
    }
}