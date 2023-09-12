package com.phorest.service;

import com.phorest.data.Client;
import com.phorest.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientServiceTest {
    private ClientService clientService;
    private ClientRepository mockClientRepository;

    @BeforeEach
    public void setUp() {
        this.mockClientRepository = Mockito.mock(ClientRepository.class);
        this.clientService = new ClientService(mockClientRepository);
    }

    @Test
    public void uploadSuccess_shouldReturnListOfClients() {
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
        List<Client> actual = clientService.save(clients);


        Client client = actual.get(0);
        assertThat(client.getId().toString()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
    }
}
