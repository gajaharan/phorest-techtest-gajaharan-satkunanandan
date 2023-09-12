package com.phorest.csvimporter;

import com.phorest.data.Client;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientCsvParserTest {
    ClientCsvParser parser = new ClientCsvParser();

    @Test
    public void emptyListReturnedWhenCsvDataStringIsNull() {
        var actual = parser.read(null);
        assertThat(actual).isEmpty();
    }

    @Test
    public void clientDataReturnedWhenSingleRowIsParsed() throws URISyntaxException, IOException {
        var csv = readFile("/csv/valid_simple_client.csv");
        var actual = parser.read(csv);

        Client client = actual.get(0);
        assertThat(client.getId().toString()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
        assertThat(client.getFirstName()).isEqualTo("Dori");
        assertThat(client.getLastName()).isEqualTo("Dietrich");
        assertThat(client.getBanned()).isEqualTo(false);
    }

    private  static InputStream readFile(String fileName) {
        return ClientCsvParserTest.class.getResourceAsStream(fileName);
    }
}
