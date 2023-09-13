package com.phorest.integration;

import com.phorest.appointment.AppointmentRepository;
import com.phorest.data.Appointment;
import com.phorest.data.PurchaseProduct;
import com.phorest.product.ProductRepository;
import com.phorest.data.Client;
import com.phorest.client.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;


import static com.phorest.PhorestConstants.*;
import static com.phorest.TestHelper.getFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadIntegrationTest {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void shouldReturn201_whenCsvFilesAreUploadedSuccessfullyAndSavedToDatabase() {
        var baseUrl = "http://localhost:" + randomServerPort + "/api/v1/upload";
        final var clientResponse =
                getResponseFor(Client.class, CLIENT_IMPORT_TYPE, "valid_simple_client.csv", baseUrl);

        assertEquals(HttpStatus.CREATED, clientResponse.getStatusCode());
        var client = clientRepository.findById("263f67fa-ce8f-447b-98cf-317656542216");
        assertThat(client.get().getId()).isEqualTo("263f67fa-ce8f-447b-98cf-317656542216");

        final var appointmentResponse =
                getResponseFor(Appointment.class, APPOINTMENT_IMPORT_TYPE, "valid_simple_appointment.csv", baseUrl);

        assertEquals(HttpStatus.CREATED, appointmentResponse.getStatusCode());
        var appointment = appointmentRepository.findById("7416ebc3-12ce-4000-87fb-82973722ebf4");
        assertThat(appointment.get().getId()).isEqualTo("7416ebc3-12ce-4000-87fb-82973722ebf4");

        final var purchaseResponse =
                getResponseFor(PurchaseProduct.class, PURCHASE_IMPORT_TYPE, "valid_simple_purchase.csv", baseUrl);

        assertEquals(HttpStatus.CREATED, purchaseResponse.getStatusCode());
        var purchaseProduct = productRepository.findById("d2d3b92d-f9b5-48c5-bf31-88c28e3b73ac");
        assertThat(purchaseProduct.get().getId()).isEqualTo("d2d3b92d-f9b5-48c5-bf31-88c28e3b73ac");

        final var serviceResponse =
                getResponseFor(PurchaseProduct.class, SERVICE_IMPORT_TYPE, "valid_simple_service.csv", baseUrl);

        assertEquals(HttpStatus.CREATED, purchaseResponse.getStatusCode());
        var serviceProduct = productRepository.findById("f1fc7009-0c44-4f89-ac98-5de9ce58095c");
        assertThat(serviceProduct.get().getId()).isEqualTo("f1fc7009-0c44-4f89-ac98-5de9ce58095c");

    }

    private ResponseEntity<Void> getResponseFor(Class T, String importType, String fileName, String baseUrl) {
        var csvFile = getFile(fileName);

        var body = new LinkedMultiValueMap<>();
        body.add("file", csvFile);

        var requestEntity = new HttpEntity<>(body, headers(importType));

        return restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, Void.class);
    }

    private HttpHeaders headers(String importType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add(HEADER_IMPORT_TYPE, importType);
        return headers;
    }
}
