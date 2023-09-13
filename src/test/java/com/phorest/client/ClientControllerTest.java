package com.phorest.client;

import com.phorest.client.model.ClientDto;
import com.phorest.data.Client;
import com.phorest.exception.PhorestDataNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    private static final String TOP_CLIENTS_ENDPOINT_URL = "/api/v1/top-clients";
    private static final String FIND_CLIENT_ENDPOINT_URL = "/api/v1/client";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    void topClientsSuccess_shouldReturn200_whenFindingWithSizeLimitAndStartDate() throws Exception {
        var amount = 2;
        var startTime = LocalDate.of(1981, 11, 21);
        var topClients = List.of(
                new ClientDto("1", "joe", "doe", "joe.doe@gmail.com", "123456789", "male", false),
                new ClientDto("2", "jane", "doe", "jane.doe@gmail.com", "123456789", "female", false)
        );
        given(clientService.findTopClients(amount, startTime.toString())).willReturn(topClients);

        var result = mockMvc.perform(
                get("%s?limit=%s&start_date=%s".formatted(TOP_CLIENTS_ENDPOINT_URL, amount, startTime))
        );

        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "id": "1",
                                "firstName": "joe",
                                "lastName": "doe",
                                "email": "joe.doe@gmail.com",
                                "phone": "123456789",
                                "gender": "male",
                                "banned": false
                            },
                            {
                                "id": "2",
                                "firstName": "jane",
                                "lastName": "doe",
                                "email": "jane.doe@gmail.com",
                                "phone": "123456789",
                                "gender": "female",
                                "banned": false
                            }
                        ]
                        """, true));
    }

    @Test
    void findClientSuccess_shouldReturn200_whenFindingClientById() throws Exception {
        var client = new Client("1", "joe", "doe", "joe.doe@gmail.com", "123456789", "male", false);
        given(clientService.findClient("1")).willReturn(client);

        var result = mockMvc.perform(get(FIND_CLIENT_ENDPOINT_URL + "/1"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "id": "1",
                            "firstName": "joe",
                            "lastName": "doe",
                            "email": "joe.doe@gmail.com",
                            "phone": "123456789",
                            "gender": "male",
                            "banned": false
                        }
                        """, true));
    }

    @Test
    void findClient_shouldReturn404_whenFindingClientWithIncorrectId() throws Exception {
        given(clientService.findClient("2")).willThrow(new PhorestDataNotFoundException(Client.class, "2"));

        var result = mockMvc.perform(get(FIND_CLIENT_ENDPOINT_URL+ "/2"));

        result.andExpect(status().isNotFound());
    }

    @Test
    void findClient_shouldReturn500_whenFindingClientThrowsException() throws Exception {
        given(clientService.findClient("2")).willThrow(new RuntimeException("Something went wrong"));

        var result = mockMvc.perform(get(FIND_CLIENT_ENDPOINT_URL+ "/2"));

        result.andExpect(status().is5xxServerError());
    }


}
