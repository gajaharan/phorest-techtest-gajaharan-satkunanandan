package com.phorest.upload;

import com.phorest.data.Client;
import com.phorest.upload.data.CsvParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.phorest.PhorestConstants.CLIENT_IMPORT_TYPE;

@Component
@RequiredArgsConstructor
public class UploadService {
    private final CsvParser<Client> clientCSVParser;

    public List<Client> upload(@NonNull InputStream csvData, @NonNull String importType) {
        switch (importType) {
            case CLIENT_IMPORT_TYPE:
                return clientCSVParser.read(csvData);
            default:
                return new ArrayList<>();
        }
    }
}

