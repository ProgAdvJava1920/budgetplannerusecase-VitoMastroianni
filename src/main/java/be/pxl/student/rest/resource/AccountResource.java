package be.pxl.student.rest.resource;

import be.pxl.student.data.Payment;

import javax.persistence.*;
import java.util.List;

public class AccountResource {

    private String IBAN;
    private String name;
    private List<Payment> payments;

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "AccountResource{" +
                "IBAN='" + IBAN + '\'' +
                ", name='" + name + '\'' +
                ", payments=" + payments +
                '}';
    }
}
