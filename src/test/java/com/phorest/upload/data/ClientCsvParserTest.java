package com.phorest.upload.data;

import com.phorest.data.Client;
import com.phorest.exception.PhorestIOException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientCsvParserTest {
    ClientCsvParser parser = new ClientCsvParser();

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
    public void clientDataReturnedWhenSingleRowIsParsed() {
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
