package com.phorest.upload;

import com.phorest.data.Client;
import com.phorest.upload.data.ClientCsvParser;
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
        assertThat(client.getId()).isEqualTo("263f67fa-ce8f-447b-98cf-317656542216");
        assertThat(client.getFirstName()).isEqualTo("Krystle");
        assertThat(client.getLastName()).isEqualTo("Harvey");
        assertThat(client.getBanned()).isEqualTo(false);
    }

    private static InputStream readFile(String fileName) {
        return ClientCsvParserTest.class.getResourceAsStream(fileName);
    }
}
