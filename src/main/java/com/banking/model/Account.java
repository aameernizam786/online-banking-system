package com.banking.model;

import java.math.BigDecimal;

public class Account {
    private long accountId;
    private int userId;
    private String accountType;
    private BigDecimal balance;

    public long getAccountId() { return accountId; }
    public void setAccountId(long id) { this.accountId = id; }
    public int getUserId() { return userId; }
    public void setUserId(int id) { this.userId = id; }
    public String getAccountType() { return accountType; }
    public void setAccountType(String t) { this.accountType = t; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal b) { this.balance = b; }
}
