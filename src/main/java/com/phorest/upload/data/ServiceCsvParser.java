package com.phorest.upload.data;

import com.opencsv.bean.CsvBindByName;
import com.phorest.data.Appointment;
import com.phorest.data.ServiceProduct;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@Component
public class ServiceCsvParser implements CsvParser<ServiceProduct> {

    @Override
    public List<ServiceProduct> read(InputStream data) {
        return read(data, ProductCsvRecord.class);
    }

    public static class ProductCsvRecord implements CsvMapper<ServiceProduct> {
        @CsvBindByName(column = "id", required = true)
        private String id;

        @CsvBindByName(column = "appointment_id", required = true)
        private String appointmentId;

        @CsvBindByName(column = "name", required = true)
        private String name;

        @CsvBindByName(column = "price", required = true)
        private BigDecimal price;

        @CsvBindByName(column = "loyalty_points", required = true)
        private long loyaltyPoints;


        @Override
        public ServiceProduct map() {
            var appointment = new Appointment();
            appointment.setId(appointmentId);
            return new ServiceProduct(
                    id,
                    appointmentId,
                    name,
                    price,
                    loyaltyPoints,
                    appointment
            );
        }
    }
}
