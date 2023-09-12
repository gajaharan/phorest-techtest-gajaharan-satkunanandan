package com.phorest.csvimporter;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import com.phorest.data.Client;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ClientCsvParser {

    public List<Client> read(InputStream data) {
        if(data != null ) {
            try (var reader = new BufferedReader(new InputStreamReader(data))) {
                return parse(reader);
            } catch (IOException ioe) {
                throw new IllegalStateException("Could not read CSV file");
            }
        }
        return new ArrayList<>();
    }

    private List<Client> parse(Reader reader) {
            List<ClientCsvRecord> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(ClientCsvRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build()
                    .parse();

            return csvToBean.stream()
                    .map(ClientCsvRecord::map)
                    .toList();
    }

    public static class ClientCsvRecord {
        @CsvBindByName(column = "id", required = true)
        private String id;
        @CsvBindByName(column = "first_name", required = true)
        private String firstName;
        @CsvBindByName(column = "last_name", required = true)
        private String lastName;
        @CsvBindByName(column = "email", required = true)
        private String email;
        @CsvBindByName(column = "phone", required = true)
        private String phone;
        @CsvBindByName(column = "gender", required = true)
        private String gender;
        @CsvBindByName(column = "banned", required = true)
        private boolean banned;

        static Client map(ClientCsvRecord record) {
            return new Client(
                    UUID.fromString(record.id),
                    record.firstName,
                    record.lastName,
                    record.email,
                    record.phone,
                    record.gender,
                    record.banned
            );
        }
    }
}
