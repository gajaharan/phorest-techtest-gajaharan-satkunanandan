package com.phorest.data;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment extends JpaEntity {

    @Builder
    public Appointment(@NotNull String id, String clientIdentifier, LocalDateTime startTime, LocalDateTime endTime, Client client) {
        this.id = id;
        this.clientIdentifier = clientIdentifier;
        this.startTime = startTime;
        this.endTime = endTime;
        this.client = client;
    }

    @Id
    @NotNull
    private String id;

    @NotNull
    private String clientIdentifier;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
