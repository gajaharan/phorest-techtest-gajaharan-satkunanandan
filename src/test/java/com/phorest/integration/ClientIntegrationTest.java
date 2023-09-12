package com.phorest.integration;

import com.phorest.csvimporter.ClientCsvParser;
import com.phorest.data.Client;
import com.phorest.repository.ClientRepository;
import com.phorest.service.ClientService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientIntegrationTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientCsvParser csvParser;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void testAddEmployeeSuccess()
    {
        var baseUrl = "http://localhost:"+randomServerPort+"/api/v1/client/upload";
        var csvFile = getFile("/csv/valid_simple_client.csv");

        var body = new LinkedMultiValueMap<>();
        body.add("file", csvFile);

        var requestEntity = new HttpEntity<>(body, headers());

        final ResponseEntity<List<Client>> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Client client = response.getBody().get(0);
        assertThat(client.getId().toString()).isEqualTo("e0b8ebfc-6e57-4661-9546-328c644a3764");
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    private FileSystemResource getFile(String fileName) {
        return new FileSystemResource(Path.of("src", "test", "resources", "csv", "valid_simple_client.csv"));
    }
}
