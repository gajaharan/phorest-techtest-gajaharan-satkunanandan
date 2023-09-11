package com.phorest.service;

import com.phorest.csvimporter.ClientCsvParser;
import com.phorest.data.Client;
import com.phorest.repository.ClientRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientCsvParser csvParser;

    @Transactional
    public List<Client> upload(@NonNull InputStream data) {
        var clients = csvParser.read(data);
        return clientRepository.saveAll(clients);
    }
}
