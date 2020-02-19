package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {

    private static final Logger logger = LogManager.getLogger(BudgetPlannerImporter.class);
    List<Account> accountList;
    private PathMatcher csvMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv");

    public void importCsv(Path path){
        if (!csvMatcher.matches(path)){
            logger.error("Invalid file: .csv expected. Provided: {}",path);
        }
        if(!Files.exists(path)){
            logger.error("File {} does not exist.", path);
        }
        try (BufferedReader reader = Files.newBufferedReader(path)){
            List<Account> accounts = new ArrayList<>();
            String line = null;
            while (( line = reader.readLine()) != null){
                String[] attributes = line.split(",");
                Account account = createAccount(attributes);
                accounts.add(account);
            }
        } catch (IOException ioe) {
            logger.fatal("An error occured while reading file {}", path);
        }
    }

    private Account createAccount(String[] metadata) {
        String name = metadata[0];
        String iban = metadata[1];
        String counterAccountIban = metadata[2];
        LocalDateTime transactionDate = LocalDateTime.parse(metadata[3]);
        Float amount = Float.parseFloat(metadata[4]);
        String currency = metadata[5];
        String detail = metadata[6];

        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment(transactionDate, amount, currency, detail);
        payments.add(payment);
        Account account = new Account();
        account.setIBAN(iban);
        account.setName(name);
        account.setPayments(payments);

        return account;
    }


}
