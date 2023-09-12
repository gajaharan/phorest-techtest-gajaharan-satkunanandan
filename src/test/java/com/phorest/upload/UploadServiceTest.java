package com.phorest.upload;

import com.phorest.upload.data.ClientCsvParser;
import com.phorest.data.Client;
import com.phorest.upload.data.CsvParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static com.phorest.PhorestConstants.CLIENT_IMPORT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UploadServiceTest {
    private UploadService uploadService;
    private CsvParser<Client> csvParser;

    @BeforeEach
    public void setUp() {
        this.csvParser = new ClientCsvParser();
        this.uploadService = new UploadService(csvParser);
    }

    @Test
    public void upload_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> uploadService.upload(null, null)
        );
    }

    @Test
    public void uploadSuccess_shouldReturnListOfClients() {
        var csv = readFile("/csv/valid_simple_client.csv");
        List<Client> clients = List.of(
                new Client(
                        UUID.fromString("e0b8ebfc-6e57-4661-9546-328c644a3764"),
                        "Dori",
                        "Dietrich",
                        "patrica@keeling.net",
                        "(272) 301-6356",
                        "Male",
                        false
                )
        );
        List<Client> actual = uploadService.upload(csv, CLIENT_IMPORT_TYPE);


        Client client = actual.get(0);
        assertThat(client.getId().toString()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
    }

    private static InputStream readFile(String fileName) {
        return ClientCsvParserTest.class.getResourceAsStream(fileName);
    }
}
