package com.phorest.data;


import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

import static com.phorest.data.ProductType.PURCHASE;

@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "purchase")
public class PurchaseProduct extends Product {
    public PurchaseProduct(String id,
                           String appointmentIdentifier,
                           String name,
                           BigDecimal price,
                           long loyaltyPoints,
                           Appointment appointment) {
        super(id, PURCHASE.value, appointmentIdentifier, name, price, loyaltyPoints, appointment);
    }
}
