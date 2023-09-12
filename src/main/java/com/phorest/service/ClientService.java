package com.phorest.service;

import com.phorest.data.Client;
import com.phorest.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public List<Client> save(List<Client> clients) {
        return clientRepository.saveAll(clients);
    }
}
