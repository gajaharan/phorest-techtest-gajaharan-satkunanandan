package com.phorest.controller;

import com.phorest.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    private static final String ENDPOINT_URL = "/api/v1/client/upload";
    private static final String FILE_NAME = "/csv/valid_simple_client.csv";
    private static final String MULTIPART_NAME = "file";
    private static final String CONTENT_TYPE = "text/csv";

    @Test
    void shouldReturn201_whenUploadValidClientFile() throws Exception {
        var file = readFile(FILE_NAME);
        var multipartFile = new MockMultipartFile(MULTIPART_NAME, FILE_NAME, CONTENT_TYPE, file);

        var result = mockMvc.perform(multipart(ENDPOINT_URL).file(multipartFile));

        result.andExpect(status().isCreated());
    }

    private  static InputStream readFile(String fileName) {
        return ClientControllerTest.class.getResourceAsStream(fileName);
    }
}
