package be.pxl.student.service;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.util.EntityManagerUtil;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.LocalDateConverter;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDao(EntityManagerUtil.createEntityManager());
    }

    public List<Payment> findPaymentsByAccountName(String name) throws AccountNotFoundException {
        Account account = accountDao.findAccountByName(name);
        if(account == null){
            throw new AccountNotFoundException(name);
        }
        return account.getPayments();
    }

    public void addPayment(String name, String counterAccountIBAN, float amount, String date, String detail) throws AccountNotFoundException {
        Account account = accountDao.findAccountByName(name);
        Account counterAccount = accountDao.findAccountByIBAN(counterAccountIBAN);
        if (account == null) {
            throw new AccountNotFoundException(name);
        }
        if (counterAccount == null) {
            throw new AccountNotFoundException(counterAccountIBAN);
        }

        LocalDateConverter converter = new LocalDateConverter();
        LocalDate paymentDate = converter.fromString(date);
        LocalDateTime paymentDateTime = LocalDateTime.of(paymentDate, LocalTime.now());

        Payment payment = new Payment();
        payment.setAccount(account);
        payment.setCounterAccount(counterAccount);
        payment.setAmount(amount);
        payment.setDate(paymentDateTime);
        payment.setDetail(detail);
        payment.setCurrency("EUR");
        account.addPayment(payment);
        accountDao.updateAccount(account);
    }
    public void addAccount(String name, String iban) throws AccountException {
        if(accountDao.findAccountByIBAN(iban) == null && accountDao.findAccountByName(name) == null){
            Account newAccount = new Account();
            newAccount.setName(name);
            newAccount.setIBAN(iban);
            accountDao.createAccount(newAccount);
        } else {
            throw new AccountException(String.format("Iban %s or name %s already exists", iban, name));
        }
    }
}
