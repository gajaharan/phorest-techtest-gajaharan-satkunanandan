package com.phorest.csvimporter;

import com.phorest.data.Client;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientCsvParserTest {
    ClientCsvParser parser = new ClientCsvParser();

    @Test
    public void emptyListReturnedWhenCsvDataStringIsNull() {
        var actual = parser.parse(null);
        assertThat(actual).isEmpty();
    }

    @Test
    public void emptyListReturnedWhenCsvDataStringIsBlank() {
        var actual = parser.parse("");
        assertThat(actual).isEmpty();
    }

    @Test
    public void clientDataReturnedWhenSingleRowIsParsed() throws URISyntaxException, IOException {
        var csv = readFileAsString("/csv/valid_simple_client.csv");

        var actual = parser.parse(csv);

        Client client = actual.get(0);
        assertThat(client.getId()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
        assertThat(client.getFirstName()).isEqualTo("Dori");
        assertThat(client.getLastName()).isEqualTo("Dietrich");
        assertThat(client.getBanned()).isEqualTo(false);
    }

    String readFileAsString(String fileName) throws URISyntaxException, IOException {
        return Files.readString(Paths.get(ClientCsvParserTest.class.getResource(fileName).toURI()));
    }
}
