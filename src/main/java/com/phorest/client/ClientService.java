package com.phorest.client;

import com.phorest.client.model.ClientDto;
import com.phorest.data.Client;
import com.phorest.exception.DataNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public List<Client> save(List<Client> clients) {
        return clientRepository.saveAll(clients);
    }

    public List<Client> findClients(@NonNull Set<String> identifiers) {
        return clientRepository.findAllByIdIn(identifiers);
    }

    public Collection<ClientDto> findTopClients(int limit, String startTime) {
        return this.clientRepository.findTopClients(limit, startTime)
                .stream()
                .map(Client::toDto)
                .toList();
    }

    public Client findClient(@NonNull String identifier) {
        return clientRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new DataNotFoundException(Client.class, identifier));
    }
}
