package com.banking.model;

import java.math.BigDecimal;

public class Account {
    private long accountId;
    private int userId;
    private String accountType;
    private BigDecimal balance = BigDecimal.ZERO;

    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "Acc #" + accountId + " | " + accountType + " | Balance: " + balance;
    }
}
