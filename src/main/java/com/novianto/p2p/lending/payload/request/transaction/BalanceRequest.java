package com.novianto.p2p.lending.payload.request.transaction;

import java.math.BigDecimal;

public class BalanceRequest {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}