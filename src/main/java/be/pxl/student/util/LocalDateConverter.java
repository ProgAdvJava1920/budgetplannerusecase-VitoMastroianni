package be.pxl.student.util;


import org.apache.johnzon.mapper.Converter;

import java.time.LocalDate;

public class LocalDateConverter implements Converter<LocalDate> {
    @Override
    public String toString(final LocalDate instance) {
        return instance.toString();
    }

    @Override
    public LocalDate fromString(final String text) {
        return LocalDate.parse(text);
    }
}