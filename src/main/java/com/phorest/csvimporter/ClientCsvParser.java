package com.phorest.csvimporter;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import com.phorest.data.Client;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ClientCsvParser {

    public List<Client> parse(String csv) {
        if (StringUtils.isNotBlank(csv)) {
            List<ClientCsvRecord> csvToBean = new CsvToBeanBuilder(new StringReader(csv))
                    .withType(ClientCsvRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build()
                    .parse();

            return csvToBean.stream()
                    .map(ClientCsvRecord::map)
                    .toList();
        }
        return new ArrayList<>();
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
                    record.id,
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
