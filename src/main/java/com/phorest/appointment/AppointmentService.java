package com.phorest.appointment;

import com.phorest.client.ClientService;
import com.phorest.data.Appointment;
import com.phorest.data.Client;
import com.phorest.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ClientService clientService;

    @Transactional
    public List<Appointment> save(List<Appointment> appointments) {
        var clientIdentifiers = extractClientIdentifiers(appointments);
        var clients = clientService.fetch(clientIdentifiers);
        var updatedAppointments = mapAppointmentsWithClient(appointments, clients);
        return appointmentRepository.saveAll(updatedAppointments);
    }

    private List<String> extractClientIdentifiers(List<Appointment> csvRecords) {
        return csvRecords.stream()
                .map(Appointment::getClientIdentifier)
                .distinct()
                .toList();
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

    private Client findClient(String  clientIdentifier, List<Client> clients) {
        return clients.stream()
                .filter(client -> Objects.equals(client.getId(), clientIdentifier))
                .findFirst().orElseThrow(() -> new DataNotFoundException(Client.class, clientIdentifier));
    }
}
