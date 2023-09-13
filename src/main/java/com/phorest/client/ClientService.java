package com.phorest.client;

import com.phorest.client.model.ClientDto;
import com.phorest.data.Client;
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

    public List<Client> fetch(@NonNull Set<String> identifiers) {
        return clientRepository.findAllByIdIn(identifiers);
    }

    public Collection<ClientDto> findTopClients(int limit, String startTime) {
        return this.clientRepository.findTopClients(limit, startTime)
                .stream()
                .map(Client::toDto)
                .toList();
    }
}
