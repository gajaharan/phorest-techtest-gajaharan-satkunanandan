package com.phorest.client;

import com.phorest.data.Client;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    List<Client> findAllByIdIn(@NonNull Set<String> ids);
}
