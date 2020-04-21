package be.pxl.student.dao;

import be.pxl.student.data.Account;
import be.pxl.student.data.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

public class AccountDao {
    private EntityManager entityManager;

    private static final Logger LOGGER = LogManager.getLogger(AccountDao.class);

    public AccountDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account findPaymentsByName(String name){
          TypedQuery<Account> findByName = entityManager.createNamedQuery("findByName", Account.class);
          findByName.setParameter("name", name);
          try{
              return findByName.getSingleResult();
          }
          catch (NoResultException ex) {
              return null;
          }
    }

    public Account findAccountByIBAN(String iban) {
        TypedQuery<Account> query = entityManager.createNamedQuery("findByIBAN", Account.class);
        LOGGER.info("query with iban [" + iban + "]");
        query.setParameter("iban",iban);
        try {
            return query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    public Account updateAccount(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        account = entityManager.merge(account);
        transaction.commit();
        return account;
    }

    public Account createAccount(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(account);
        transaction.commit();
        return account;
    }

    public void addPayment(String name, String counterAccountIBAN, float amount, String detail) throws AccountNotFoundException {
        Account account = findPaymentsByName(name);
        if (account == null) {
            throw new AccountNotFoundException(name);
        }
        Account counterAccount = findAccountByIBAN(counterAccountIBAN);
        if (counterAccount == null) {
            counterAccount = new Account();
            counterAccount.setIBAN(counterAccountIBAN);
            counterAccount = createAccount(counterAccount);
        }
        Payment payment = new Payment();
        payment.setCounterAccount(counterAccount);
        payment.setAmount(amount);
        payment.setCurrency("EUR");
        payment.setDate(LocalDateTime.now());
        payment.setDetail(detail);
        account.addPayment(payment);
        updateAccount(account);
    }


}
