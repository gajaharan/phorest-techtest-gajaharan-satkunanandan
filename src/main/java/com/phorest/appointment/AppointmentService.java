package com.phorest.appointment;

import com.phorest.client.ClientService;
import com.phorest.data.Appointment;
import com.phorest.data.Client;
import com.phorest.exception.PhorestDataNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ClientService clientService;

    @Transactional
    public List<Appointment> save(List<Appointment> appointments) {
        var clientIdentifiers = extractClientIdentifiers(appointments);
        var clients = clientService.findClients(clientIdentifiers);
        var updatedAppointments = mapAppointmentsWithClient(appointments, clients);
        return appointmentRepository.saveAll(updatedAppointments);
    }

    public List<Appointment> fetch(@NonNull Set<String> identifiers) {
        return appointmentRepository.findAllByIdIn(identifiers);
    }

    private Set<String> extractClientIdentifiers(List<Appointment> appointments) {
        return appointments.stream()
                .map(Appointment::getClientIdentifier)
                .collect(Collectors.toUnmodifiableSet());
    }

    private List<Appointment> mapAppointmentsWithClient(List<Appointment> appointments, List<Client> clients) {
        return appointments.stream()
                .map(appointment -> new Appointment(
                        appointment.getId(),
                        appointment.getClientIdentifier(),
                        appointment.getStartTime(),
                        appointment.getEndTime(),
                        findClient(appointment.getClientIdentifier(), clients)
                )).toList();
    }

    private Client findClient(String clientIdentifier, List<Client> clients) {
        return clients.stream()
                .filter(client -> client.getId().equals(clientIdentifier))
                .findFirst().orElseThrow(() -> new PhorestDataNotFoundException(Client.class, clientIdentifier));
    }
}
