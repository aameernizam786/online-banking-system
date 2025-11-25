package com.banking.service;



import com.banking.dao.AccountDAO;
import com.banking.dao.TransactionDAO;
import com.banking.model.Account;
import com.banking.model.TransactionRecord;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BankService {
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public void transfer(long fromAccountId, long toAccountId, BigDecimal amount) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        Account a1 = accountDAO.findById(fromAccountId);
        Account a2 = accountDAO.findById(toAccountId);
        if (a1 == null || a2 == null) throw new IllegalArgumentException("Account not found");

        Object lock1 = a1.getAccountId() < a2.getAccountId() ? a1 : a2;
        Object lock2 = lock1 == a1 ? a2 : a1;

        synchronized (lock1) {
            synchronized (lock2) {
                if (a1.getBalance().compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds");
                a1.setBalance(a1.getBalance().subtract(amount));
                a2.setBalance(a2.getBalance().add(amount));
                accountDAO.update(a1);
                accountDAO.update(a2);

                TransactionRecord t1 = new TransactionRecord();
                t1.setAccountId(a1.getAccountId());
                t1.setType("TRANSFER_OUT");
                t1.setAmount(amount);
                t1.setDescription("Transfer to " + a2.getAccountId());
                t1.setTimestamp(LocalDateTime.now());
                transactionDAO.save(t1);

                TransactionRecord t2 = new TransactionRecord();
                t2.setAccountId(a2.getAccountId());
                t2.setType("TRANSFER_IN");
                t2.setAmount(amount);
                t2.setDescription("Transfer from " + a1.getAccountId());
                t2.setTimestamp(LocalDateTime.now());
                transactionDAO.save(t2);
            }
        }
    }

    public void deposit(long accountId, BigDecimal amount) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        Account a = accountDAO.findById(accountId);
        if (a == null) throw new IllegalArgumentException("Account not found");
        synchronized (a) {
            a.setBalance(a.getBalance().add(amount));
            accountDAO.update(a);
            TransactionRecord tr = new TransactionRecord();
            tr.setAccountId(a.getAccountId());
            tr.setType("DEPOSIT");
            tr.setAmount(amount);
            tr.setTimestamp(LocalDateTime.now());
            tr.setDescription("Deposit");
            transactionDAO.save(tr);
        }
    }

    public void withdraw(long accountId, BigDecimal amount) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        Account a = accountDAO.findById(accountId);
        if (a == null) throw new IllegalArgumentException("Account not found");
        synchronized (a) {
            if (a.getBalance().compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds");
            a.setBalance(a.getBalance().subtract(amount));
            accountDAO.update(a);
            TransactionRecord tr = new TransactionRecord();
            tr.setAccountId(a.getAccountId());
            tr.setType("WITHDRAWAL");
            tr.setAmount(amount);
            tr.setTimestamp(LocalDateTime.now());
            tr.setDescription("Withdrawal");
            transactionDAO.save(tr);
        }
    }
}
