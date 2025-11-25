package com.banking.model;

import java.math.BigDecimal;

public class TransactionRecord {
    private long accountId;
    private String type;
    private BigDecimal amount;
    private String description;

    public long getAccountId() { return accountId; }
    public void setAccountId(long id) { this.accountId = id; }
    public String getType() { return type; }
    public void setType(String t) { this.type = t; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal a) { this.amount = a; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
}
