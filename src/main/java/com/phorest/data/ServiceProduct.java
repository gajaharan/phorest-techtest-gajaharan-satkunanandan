package com.phorest.data;


import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

import static com.phorest.data.ProductType.SERVICE;

@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "service")
public class ServiceProduct extends Product {
    public ServiceProduct(String id,
                          String appointmentIdentifier,
                          String name,
                          BigDecimal price,
                          long loyaltyPoints,
                          Appointment appointment) {
        super(id, SERVICE.value, appointmentIdentifier, name, price, loyaltyPoints, appointment);
    }
}
