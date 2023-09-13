package com.phorest.data;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name = "products")
public abstract class Product extends DataEntity {

    public Product(String id, String type, String appointmentIdentifier, String name, BigDecimal price, long loyaltyPoints, Appointment appointment) {
        this.id = id;
        this.type = type;
        this.appointmentIdentifier = appointmentIdentifier;
        this.name = name;
        this.price = price;
        this.loyaltyPoints = loyaltyPoints;
        this.appointment = appointment;
    }

    @Id
    @NotNull
    private String id;

    @Column(nullable = false, insertable = false, updatable = false)
    private String type;

    @NotNull
    private String appointmentIdentifier;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private long loyaltyPoints;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

}

