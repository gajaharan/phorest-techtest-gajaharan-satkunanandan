package com.phorest.integration;

import com.phorest.appointment.AppointmentRepository;
import com.phorest.data.Appointment;
import com.phorest.upload.data.AppointmentCsvParser;
import com.phorest.upload.data.ClientCsvParser;
import com.phorest.data.Client;
import com.phorest.client.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.phorest.PhorestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadIntegrationTest {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void shouldReturn201_whenClientCsvFileIsUploadedSuccessfullyAndSavedToDatabase() {
        var baseUrl = "http://localhost:" + randomServerPort + "/api/v1/upload";
        var csvFile = getFile("valid_simple_client.csv");

        var body = new LinkedMultiValueMap<>();
        body.add("file", csvFile);

        var requestEntity = new HttpEntity<>(body, headers(CLIENT_IMPORT_TYPE));

        final ResponseEntity<List<Client>> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
        });

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Optional<Client> client = clientRepository.findById("263f67fa-ce8f-447b-98cf-317656542216");
        assertThat(client.get().getId()).isEqualTo("263f67fa-ce8f-447b-98cf-317656542216");
    }

    @Test
    public void shouldReturn201_whenAppointmentCsvFileIsUploadedSuccessfullyAndSavedToDatabase() {
        var baseUrl = "http://localhost:" + randomServerPort + "/api/v1/upload";

        var csvFile = getFile("valid_simple_client.csv");

        var body = new LinkedMultiValueMap<>();
        body.add("file", csvFile);

        var requestEntity = new HttpEntity<>(body, headers(CLIENT_IMPORT_TYPE));

        ResponseEntity<List<Client>> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
        });

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        List<Client> clients = clientRepository.findAll();
        assertThat(clients.get(0).getId()).isEqualTo("263f67fa-ce8f-447b-98cf-317656542216");

        csvFile = getFile("valid_simple_appointment.csv");

        body = new LinkedMultiValueMap<>();
        body.add("file", csvFile);

        requestEntity = new HttpEntity<>(body, headers(APPOINTMENT_IMPORT_TYPE));

        response = restTemplate
                .exchange(baseUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Optional<Appointment> appointment = appointmentRepository.findById("7416ebc3-12ce-4000-87fb-82973722ebf4");
        assertThat(appointment.get().getId()).isEqualTo("7416ebc3-12ce-4000-87fb-82973722ebf4");
    }

    private HttpHeaders headers(String importType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add(HEADER_IMPORT_TYPE, importType);
        return headers;
    }

    private FileSystemResource getFile(String fileName) {
        return new FileSystemResource(Path.of("src", "test", "resources", "csv", fileName));
    }
}
