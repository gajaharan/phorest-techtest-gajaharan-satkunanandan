package com.phorest.upload;

import com.phorest.data.Appointment;
import com.phorest.upload.data.AppointmentCsvParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentCsvParserTest {
    AppointmentCsvParser parser = new AppointmentCsvParser();

    @Test
    public void emptyListReturnedWhenCsvDataStringIsNull() {
        var actual = parser.read(null);
        assertThat(actual).isEmpty();
    }

    @Test
    public void appointmentDataReturnedWhenSingleRowIsParsed() throws URISyntaxException, IOException {
        var csv = readFile("/csv/valid_simple_appointment.csv");
        var actual = parser.read(csv);

        Appointment appointment = actual.get(0);
        assertThat(appointment.getId()).isEqualTo("7416ebc3-12ce-4000-87fb-82973722ebf4");
        assertThat(appointment.getClientIdentifier()).isEqualTo("263f67fa-ce8f-447b-98cf-317656542216");
        assertThat(appointment.getStartTime()).isEqualTo("2016-02-07T17:15");
        assertThat(appointment.getEndTime()).isEqualTo("2016-02-07T20:15");
    }

    private static InputStream readFile(String fileName) {
        return AppointmentCsvParserTest.class.getResourceAsStream(fileName);
    }
}
