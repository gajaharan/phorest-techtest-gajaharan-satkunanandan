package com.phorest.client;

import com.phorest.client.model.ClientDto;
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
    private static final String ENDPOINT_URL = "/api/v1/top-clients";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    void topClients_shouldReturn200_whenFindingWithSizeLimitAndStartDate() throws Exception {
        var amount = 2;
        var startTime = LocalDate.of(1981, 11, 21);
        var topClients = List.of(
                new ClientDto("1", "joe", "doe", "joe.doe@gmail.com", "123456789", "male", false),
                new ClientDto("2", "jane", "doe", "jane.doe@gmail.com", "123456789", "female", false)
        );
        given(clientService.findTopClients(amount, startTime.toString())).willReturn(topClients);

        var result = mockMvc.perform(
                get("%s?limit=%s&start_date=%s".formatted(ENDPOINT_URL, amount, startTime))
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
}
