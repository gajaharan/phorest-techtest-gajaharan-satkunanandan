package com.phorest.upload.data;

import com.phorest.data.ServiceProduct;
import com.phorest.exception.PhorestIOException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceCsvParserTest {
    ServiceCsvParser parser = new ServiceCsvParser();

    @Test
    public void emptyListReturnedWhenCsvDataStringIsNull() {
        var actual = parser.read(null);
        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldThrowPhorestIOException() {
        var csv = readFile("");
        assertThrows(PhorestIOException.class,
                () -> parser.read(csv)
        );
    }

    @Test
    public void serviceDataReturnedWhenSingleRowIsParsed() {
        var csv = readFile("/csv/valid_simple_service.csv");
        var actual = parser.read(csv);

        ServiceProduct purchase = actual.get(0);
        assertThat(purchase.getId()).isEqualTo("f1fc7009-0c44-4f89-ac98-5de9ce58095c");
        assertThat(purchase.getName()).isEqualTo("Full Head Colour");
        assertThat(purchase.getPrice()).isEqualTo(new BigDecimal("85.0"));
        assertThat(purchase.getLoyaltyPoints()).isEqualTo(80);
    }

    private static InputStream readFile(String fileName) {
        return ServiceCsvParserTest.class.getResourceAsStream(fileName);
    }
}
