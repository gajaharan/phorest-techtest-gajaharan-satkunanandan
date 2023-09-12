package com.phorest.upload.data;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public interface CsvParser<T> {
    List<T> read(InputStream data);

    default List<T> read(InputStream data, Class<? extends CsvMapper<T>> mapperClass) {
        if (data != null) {
            try (var reader = new BufferedReader(new InputStreamReader(data))) {
                return parse(reader, mapperClass);
            } catch (IOException ioe) {
                throw new IllegalStateException("Could not read CSV file");
            }
        }
        return new ArrayList<>();
    }

    private List<T> parse(Reader reader, Class<? extends CsvMapper<T>> mapperClass) {
        List<CsvMapper<T>> csvToBean = new CsvToBeanBuilder(reader)
                .withType(mapperClass)
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build()
                .parse();

        return csvToBean.stream()
                .map(CsvMapper::map)
                .toList();
    }
}
