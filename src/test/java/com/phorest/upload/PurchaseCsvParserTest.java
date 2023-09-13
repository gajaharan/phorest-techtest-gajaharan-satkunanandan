package com.phorest.upload;

import com.phorest.data.PurchaseProduct;
import com.phorest.upload.data.PurchaseCsvParser;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseCsvParserTest {
    PurchaseCsvParser parser = new PurchaseCsvParser();

    @Test
    public void emptyListReturnedWhenCsvDataStringIsNull() {
        var actual = parser.read(null);
        assertThat(actual).isEmpty();
    }

    @Test
    public void purchaseDataReturnedWhenSingleRowIsParsed() {
        var csv = readFile("/csv/valid_simple_purchase.csv");
        var actual = parser.read(csv);

        PurchaseProduct purchase = actual.get(0);
        assertThat(purchase.getId()).isEqualTo("d2d3b92d-f9b5-48c5-bf31-88c28e3b73ac");
        assertThat(purchase.getName()).isEqualTo("Shampoo");
        assertThat(purchase.getPrice()).isEqualTo(new BigDecimal("19.5"));
        assertThat(purchase.getLoyaltyPoints()).isEqualTo(20);
    }

    private static InputStream readFile(String fileName) {
        return PurchaseCsvParserTest.class.getResourceAsStream(fileName);
    }
}
