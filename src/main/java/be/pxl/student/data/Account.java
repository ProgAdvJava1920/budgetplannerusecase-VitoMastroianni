package be.pxl.student.data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Accounts")
@NamedQueries(@NamedQuery(name = "findByName", query = "SELECT a FROM Account a Where a.name = :name"))
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 34)
    private String IBAN;
    @Column(length = 45)
    private String name;
    @OneToMany(mappedBy = "account")
    private List<Payment> payments;

    public Long getId() { return id; }

    public String getIBAN() { return IBAN; }

    public void setIBAN(String IBAN) { this.IBAN = IBAN; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Payment> getPayments() { return payments; }

    public void setPayments(List<Payment> payments) { this.payments = payments; }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", IBAN='" + IBAN + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
