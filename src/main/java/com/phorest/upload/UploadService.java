package com.phorest.upload;

import com.phorest.data.Appointment;
import com.phorest.data.Client;
import com.phorest.data.JpaEntity;
import com.phorest.upload.data.CsvParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.phorest.PhorestConstants.APPOINTMENT_IMPORT_TYPE;
import static com.phorest.PhorestConstants.CLIENT_IMPORT_TYPE;

@Component
@RequiredArgsConstructor
public class UploadService {
    private final CsvParser<Client> clientCSVParser;
    private final CsvParser<Appointment> appointmentCsvParser;

    public <T> List<? extends JpaEntity> upload(@NonNull InputStream csvData, @NonNull String importType) {
        switch (importType) {
            case CLIENT_IMPORT_TYPE:
                return clientCSVParser.read(csvData);
            case APPOINTMENT_IMPORT_TYPE:
                return appointmentCsvParser.read(csvData);
            default:
                return new ArrayList<>();
        }
    }
}

