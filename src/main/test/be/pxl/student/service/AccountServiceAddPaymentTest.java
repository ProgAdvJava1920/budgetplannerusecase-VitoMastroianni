package be.pxl.student.service;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.MapKeyColumn;
import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountServiceAddPaymentTest {
    private static final String NAME = "Test";
    private static final String IBAN = "BE123456789";
    private static final String COUNTER_NAME = "Counter Test";
    private static final String COUNTER_IBAN = "BE987654321";



    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountService accountService;
    private Account account;
    private Account counterAccount;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        account = new Account();
        account.setIBAN(IBAN);
        account.setName(NAME);

        counterAccount = new Account();
        counterAccount.setIBAN(COUNTER_IBAN);
        counterAccount.setName(COUNTER_NAME);

    }


    @Test
    public void accountCanBeFoundByIban(){
        when(accountDao.findAccountByIBAN(IBAN)).thenReturn(account);
        assertEquals(account, accountDao.findAccountByIBAN(IBAN));

    }
    @Test
    public void newPaymentIsAddedToAnExistingAccount() throws AccountNotFoundException {
        when(accountDao.findAccountByName(NAME)).thenReturn(account);
        accountService.addPayment(NAME, "BE789456123", 50F, "20/04/2019", "TEST");
        assertEquals(account.getPayments(), accountService.findPaymentsByAccountName(NAME));
    }
}
