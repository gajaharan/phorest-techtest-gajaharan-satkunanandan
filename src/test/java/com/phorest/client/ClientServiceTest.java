package com.phorest.client;

import com.phorest.data.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

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
    public void saveClientsSuccess_shouldSaveClientsSuccessfully() {
        List<Client> clients = List.of(
                new Client(
                        "e0b8ebfc-6e57-4661-9546-328c644a3764",
                        "Dori",
                        "Dietrich",
                        "patrica@keeling.net",
                        "(272) 301-6356",
                        "Male",
                        false
                )
        );
        Mockito.when(mockClientRepository.saveAll(Mockito.eq(clients))).thenReturn(clients);
        var actual = clientService.save(clients);

        Client client = actual.get(0);
        assertThat(client.getId()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
    }

    @Test
    public void findClientSuccess_shouldReturnAClient() {
        Client client = new Client(
                        "e0b8ebfc-6e57-4661-9546-328c644a3764",
                        "Dori",
                        "Dietrich",
                        "patrica@keeling.net",
                        "(272) 301-6356",
                        "Male",
                        false
                );
        Mockito.when(mockClientRepository.findByIdentifier("e0b8ebfc-6e57-4661-9546-328c644a3764")).thenReturn(Optional.of(client));
        var actual = clientService.findClient("e0b8ebfc-6e57-4661-9546-328c644a3764");

        assertThat(client.getId()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
    }
}
