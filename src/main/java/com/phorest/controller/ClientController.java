package com.phorest.controller;

import com.phorest.data.Client;
import com.phorest.service.ClientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping(path = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Client>> upload(@Valid @RequestPart(value = "file", required = false) @NonNull MultipartFile file) throws IOException {
        var csvData = file.getInputStream();
        List<Client> clients = clientService.upload(csvData);
        return ResponseEntity.created(null).body(clients);
    }
}
