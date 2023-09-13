package com.phorest.upload;

import com.phorest.data.Appointment;
import com.phorest.data.PurchaseProduct;
import com.phorest.data.ServiceProduct;
import com.phorest.upload.data.AppointmentCsvParser;
import com.phorest.upload.data.ClientCsvParser;
import com.phorest.data.Client;
import com.phorest.upload.data.CsvParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static com.phorest.PhorestConstants.CLIENT_IMPORT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UploadServiceTest {
    private UploadService uploadService;
    private CsvParser<Client> clientCsvParser;
    private CsvParser<Appointment> appointmentCsvParser;
    private CsvParser<PurchaseProduct> purchaseCsvParser;
    private CsvParser<ServiceProduct> serviceCsvParser;

    @BeforeEach
    public void setUp() {
        this.clientCsvParser = new ClientCsvParser();
        this.appointmentCsvParser = new AppointmentCsvParser();
        this.uploadService = new UploadService(clientCsvParser, appointmentCsvParser, purchaseCsvParser, serviceCsvParser);
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
        List<Client> actual = (List<Client>) uploadService.upload(csv, CLIENT_IMPORT_TYPE);

        Client client = actual.get(0);
        assertThat(client.getId()).isEqualTo("263f67fa-ce8f-447b-98cf-317656542216");
    }

    private static InputStream readFile(String fileName) {
        return ClientCsvParserTest.class.getResourceAsStream(fileName);
    }
}
