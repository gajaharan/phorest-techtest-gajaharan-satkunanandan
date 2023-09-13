package com.phorest.upload.data;

import com.opencsv.bean.CsvBindByName;
import com.phorest.data.Client;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class ClientCsvParser implements CsvParser<Client> {

    @Override
    public List<Client> read(InputStream data) {
        return read(data, ClientCsvRecord.class);
    }

    public static class ClientCsvRecord implements CsvMapper<Client> {
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


        @Override
        public Client map() {
            return new Client(
                    id,
                    firstName,
                    lastName,
                    email,
                    phone,
                    gender,
                    banned
            );
        }
    }
}
