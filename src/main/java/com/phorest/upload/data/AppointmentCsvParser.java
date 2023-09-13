package com.phorest.upload.data;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.phorest.data.Appointment;
import com.phorest.data.Client;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AppointmentCsvParser implements CsvParser<Appointment> {

    @Override
    public List<Appointment> read(InputStream data) {
        return read(data, AppointmentCsvRecord.class);
    }

    public static class AppointmentCsvRecord implements CsvMapper<Appointment> {
        @CsvBindByName(column = "id", required = true)
        private String id;

        @CsvBindByName(column = "client_id", required = true)
        private String clientId;

        @CsvDate(value = "yyyy-MM-dd HH:mm:ss X")
        @CsvBindByName(column = "start_time", required = true)
        private LocalDateTime startTime;

        @CsvDate(value = "yyyy-MM-dd HH:mm:ss X")
        @CsvBindByName(column = "end_time", required = true)
        private LocalDateTime endTime;


        @Override
        public Appointment map() {
            var client = new Client();
            client.setId(clientId);
            return new Appointment(
                    id,
                    clientId,
                    startTime,
                    endTime,
                    client
            );
        }
    }
}
