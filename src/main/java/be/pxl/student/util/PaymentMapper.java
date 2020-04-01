package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaymentMapper {

    /* Jos,BE69771770897312,BE17795215960626,Thu Feb 13 05:47:35 CET 2020,265.8,EUR,Ut ut necessitatibus itaque ullam. */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

    public Payment map(String validLine) throws InvalidPaymentException {
        String[] data = validLine.split(",");
        if(data.length != 7){
            throw new InvalidPaymentException("Invalid number of fields in line");
        }

        return new Payment(LocalDateTime.parse(data[3], FORMATTER), Float.parseFloat(data[4]), data[5], data[6]);
    }
}
