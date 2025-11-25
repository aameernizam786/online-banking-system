package com.banking.service;

import com.banking.dao.*;
import com.banking.model.*;
import java.math.BigDecimal;

public class BankService {

    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO txDAO = new TransactionDAO();

    public void deposit(long accId, BigDecimal amt) throws Exception {
        Account a = accountDAO.findById(accId);
        a.setBalance(a.getBalance().add(amt));
        accountDAO.update(a);

        TransactionRecord t = new TransactionRecord();
        t.setAccountId(accId);
        t.setType("DEPOSIT");
        t.setAmount(amt);
        t.setDescription("Deposit");
        txDAO.save(t);
    }

    public void withdraw(long accId, BigDecimal amt) throws Exception {
        Account a = accountDAO.findById(accId);
        if (a.getBalance().compareTo(amt) < 0)
            throw new IllegalArgumentException("Insufficient balance");

        a.setBalance(a.getBalance().subtract(amt));
        accountDAO.update(a);

        TransactionRecord t = new TransactionRecord();
        t.setAccountId(accId);
        t.setType("WITHDRAW");
        t.setAmount(amt);
        t.setDescription("Withdraw");
        txDAO.save(t);
    }
}
