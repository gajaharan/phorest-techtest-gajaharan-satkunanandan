package com.phorest.integration;

import com.phorest.client.ClientRepository;
import com.phorest.client.model.ClientDto;
import com.phorest.data.Appointment;
import com.phorest.data.Client;
import com.phorest.data.PurchaseProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static com.phorest.PhorestConstants.*;
import static com.phorest.TestHelper.getFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientIntegrationTest {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @BeforeEach
    public void Setup() {
        setupDatabase();
    }

    @Test
    public void shouldReturn200_whenCsvFilesAreUploadedSuccessfullyAndSavedToDatabase() {
        var baseUrl = "http://localhost:" + randomServerPort + "/api/v1/top-clients?limit=3&start_date=2016-11-01";
        var responseType = new ParameterizedTypeReference<List<ClientDto>>() {};
        final var response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, responseType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var clients = response.getBody();
        assertEquals(3, clients.size());
    }

    private void setupDatabase() {
        load(Client.class, CLIENT_IMPORT_TYPE, "valid_simple_client.csv");
        load(Appointment.class, APPOINTMENT_IMPORT_TYPE, "valid_simple_appointment.csv");
        load(PurchaseProduct.class, PURCHASE_IMPORT_TYPE, "valid_simple_purchase.csv");
        load(PurchaseProduct.class, SERVICE_IMPORT_TYPE, "valid_simple_service.csv");
    }

    void load(Class T, String importType, String fileName) {
        var body = new LinkedMultiValueMap<>();
        body.add("file", getFile(fileName));
        var requestEntity = new HttpEntity<>(body, headers(importType));
        restTemplate.exchange(
                "http://localhost:" + randomServerPort + "/api/v1/upload",
                HttpMethod.POST,
                requestEntity,
                Void.class
        );
    }

    private HttpHeaders headers(String importType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add(HEADER_IMPORT_TYPE, importType);
        return headers;
    }

}
