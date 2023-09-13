package com.phorest.client;

import com.phorest.data.Client;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    List<Client> findAllByIdIn(@NonNull Set<String> ids);

    @Query(
            value = " SELECT clients.* FROM clients " +
                    " INNER JOIN appointments ON appointments.client_id = clients.id " +
                    " INNER JOIN products ON products.appointment_id = appointments.id " +
                    " WHERE appointments.end_time > :startTime " +
                    " AND clients.banned = false " +
                    " GROUP BY clients.id " +
                    " ORDER BY SUM(products.loyalty_points) DESC " +
                    " LIMIT :maxLimit " +
                    " ; ",
            nativeQuery = true)
    Collection<Client> findTopClients(int maxLimit, String startTime);
}
