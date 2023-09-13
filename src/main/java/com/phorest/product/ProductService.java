package com.phorest.product;

import com.phorest.appointment.AppointmentService;
import com.phorest.data.*;
import com.phorest.exception.PhorestDataNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.phorest.data.ProductType.PURCHASE;
import static com.phorest.data.ProductType.SERVICE;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final AppointmentService appointmentService;

    @Transactional
    public void saveServices(@NonNull List<Product> products) {
        saveProducts(products, SERVICE);
    }

    @Transactional
    public void savePurchases(@NonNull List<Product> products) {
        saveProducts(products, PURCHASE);
    }

    private List<Product> saveProducts(List<Product> products, ProductType type) {
        var appointmentIdentifiers = extractAppointmentIdentifiers(products);
        var appointments = appointmentService.fetch(appointmentIdentifiers);
        var updatedProducts = mapProductsWithAppointment(products, appointments, type);
        return productRepository.saveAll(updatedProducts);
    }

    private Set<String> extractAppointmentIdentifiers(List<Product> products) {
        return products.stream()
                .map(Product::getAppointmentIdentifier)
                .collect(Collectors.toUnmodifiableSet());
    }

    private List<Product> mapProductsWithAppointment(List<Product> products, List<Appointment> appointments, ProductType type) {
        return products.stream()
                .map(product -> mapTo(product, findAppointment(product, appointments), type))
                .toList();
    }

    private Product mapTo(Product product,
                          Appointment appointment,
                          ProductType type) {
        return switch (type) {
            case SERVICE -> new ServiceProduct(
                    product.getId(),
                    product.getAppointmentIdentifier(),
                    product.getName(),
                    product.getPrice(),
                    product.getLoyaltyPoints(),
                    appointment);
            case PURCHASE -> new PurchaseProduct(
                    product.getId(),
                    product.getAppointmentIdentifier(),
                    product.getName(),
                    product.getPrice(),
                    product.getLoyaltyPoints(),
                    appointment);
        };
    }

    private Appointment findAppointment(Product product, List<Appointment> appointments) {
        return appointments.stream()
                .filter(appointment -> appointment.getId().equals(product.getAppointmentIdentifier()))
                .findFirst().orElseThrow(() -> new PhorestDataNotFoundException(Client.class, product.getAppointmentIdentifier()));
    }
}
