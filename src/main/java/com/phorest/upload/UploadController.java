package com.phorest.upload;

import com.phorest.data.Client;
import com.phorest.service.ClientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.phorest.PhorestConstants.HEADER_IMPORT_TYPE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;
    private final ClientService clientService;

    @PostMapping(path = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Client>> upload(
            @Valid @RequestPart(value = "file", required = false)
            @NonNull MultipartFile file,
            @RequestHeader(HEADER_IMPORT_TYPE) String importType
    ) throws IOException {
        var csvData = file.getInputStream();
        List<Client> clients = uploadService.upload(csvData, importType);
        clients = clientService.save(clients);
        return ResponseEntity.created(null).body(clients);
    }
}
