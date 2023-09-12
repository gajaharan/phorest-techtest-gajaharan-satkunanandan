package com.phorest.service;

import com.phorest.csvimporter.ClientCsvParser;
import com.phorest.csvimporter.ClientCsvParserTest;
import com.phorest.data.Client;
import com.phorest.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientServiceTest {
    private ClientService clientService;
    private ClientCsvParser csvParser;
    private ClientRepository mockClientRepository;

    @BeforeEach
    public void setUp() {
        this.mockClientRepository = Mockito.mock(ClientRepository.class);
        this.csvParser = new ClientCsvParser();
        this.clientService = new ClientService(mockClientRepository, csvParser);
    }

    @Test
    public void upload_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> clientService.upload(null)
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
        Mockito.when(mockClientRepository.saveAll(Mockito.eq(clients))).thenReturn(clients);
        List<Client> actual = clientService.upload(csv);


        Client client = actual.get(0);
        assertThat(client.getId().toString()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
    }

    private static InputStream readFile(String fileName) {
        return ClientCsvParserTest.class.getResourceAsStream(fileName);
    }
}
