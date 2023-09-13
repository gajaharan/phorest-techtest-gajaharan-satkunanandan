package com.phorest.upload;

import com.phorest.data.*;
import com.phorest.upload.data.CsvParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.phorest.PhorestConstants.*;

@Component
@RequiredArgsConstructor
public class UploadService {
    private final CsvParser<Client> clientCSVParser;
    private final CsvParser<Appointment> appointmentCsvParser;
    private final CsvParser<PurchaseProduct> purchaseCsvParser;
    private final CsvParser<ServiceProduct> serviceCsvParser;

    public <T> List<? extends JpaEntity> upload(@NonNull InputStream csvData, @NonNull String importType) {
        switch (importType) {
            case CLIENT_IMPORT_TYPE:
                return clientCSVParser.read(csvData);
            case APPOINTMENT_IMPORT_TYPE:
                return appointmentCsvParser.read(csvData);
            case PURCHASE_IMPORT_TYPE:
                return purchaseCsvParser.read(csvData);
            case SERVICE_IMPORT_TYPE:
                return serviceCsvParser.read(csvData);
            default:
                return new ArrayList<>();
        }
    }
}

