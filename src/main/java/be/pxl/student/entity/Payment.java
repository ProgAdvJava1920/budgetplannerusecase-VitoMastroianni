package be.pxl.student.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Account account;
    @ManyToOne
    private Account counterAccount;
    private LocalDateTime date;
    private float amount;
    @Column(length = 45)
    private String currency;
    private String detail;

    public Payment() {
        //JPA only
    }

    public Payment(LocalDateTime date, float amount, String currency, String detail){
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.detail = detail;
    }

    public Payment(Account account, Account counterAccount, LocalDateTime date, float amount, String currency, String detail){
        this(date, amount, currency, detail);
        this.account = account;
        this.counterAccount = counterAccount;
    }

    public int getId() { return id; }

    public Account getAccount() { return account; }

    public void setAccount(Account account) { this.account = account; }

    public Account getCounterAccount() { return counterAccount; }

    public void setCounterAccount(Account counterAccount) { this.counterAccount = counterAccount; }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) { this.date = date; }

    public float getAmount() { return amount; }

    public void setAmount(float amount) { this.amount = amount; }

    public String getCurrency() { return currency; }

    public void setCurrency(String currency) { this.currency = currency; }

    public String getDetail() { return detail; }

    public void setDetail(String detail) { this.detail = detail; }


    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", account=" + account +
                ", counterAccount=" + counterAccount +
                ", date=" + date +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
