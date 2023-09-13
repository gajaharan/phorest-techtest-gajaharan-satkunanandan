package com.phorest.upload;

import com.phorest.appointment.AppointmentService;
import com.phorest.client.ClientService;
import com.phorest.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static com.phorest.PhorestConstants.CLIENT_IMPORT_TYPE;
import static com.phorest.PhorestConstants.HEADER_IMPORT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UploadController.class)
public class UploadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private UploadService uploadService;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private ProductService productService;

    private static final String ENDPOINT_URL = "/api/v1/upload";
    private static final String FILE_NAME = "/csv/valid_simple_client.csv";
    private static final String MULTIPART_NAME = "file";
    private static final String CONTENT_TYPE = "text/csv";

    @Test
    void shouldReturn201_whenUploadValidClientFile() throws Exception {
        var file = readFile(FILE_NAME);
        var multipartFile = new MockMultipartFile(MULTIPART_NAME, FILE_NAME, CONTENT_TYPE, file);

        var result = mockMvc
                .perform(multipart(ENDPOINT_URL)
                        .file(multipartFile)
                        .header(HEADER_IMPORT_TYPE, CLIENT_IMPORT_TYPE));

        result.andExpect(status().isCreated());
    }

    private  static InputStream readFile(String fileName) {
        return UploadControllerTest.class.getResourceAsStream(fileName);
    }
}
