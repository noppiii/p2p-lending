package com.novianto.p2p.lending.payload.request.transaction;

import java.math.BigDecimal;
import java.util.Objects;

public class LenderOfferRequest {

    private BigDecimal amount;
    private Double interestRate;
    private Integer termInMonths;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getTermInMonths() {
        return termInMonths;
    }

    public void setTermInMonths(Integer termInMonths) {
        this.termInMonths = termInMonths;
    }

    @Override
    public String toString() {
        return "LenderOfferRequest{" +
                "amount=" + amount +
                ", interestRate=" + interestRate +
                ", termInMonths=" + termInMonths +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LenderOfferRequest that = (LenderOfferRequest) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(interestRate, that.interestRate) &&
                Objects.equals(termInMonths, that.termInMonths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, interestRate, termInMonths);
    }
}
