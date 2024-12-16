package com.novianto.p2p.lending.payload.response.transaction;

import com.novianto.p2p.lending.model.enumType.OfferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class LenderOfferResponse {
    private Long id;
    private BigDecimal amount;
    private Double interestRate;
    private Integer termInMonths;
    private OfferStatus status;
    private LocalDateTime createdAt;
    private Long lenderId;
    private String lenderNickname;

    public LenderOfferResponse() {
    }

    public LenderOfferResponse(Long id, BigDecimal amount, Double interestRate, Integer termInMonths,
                               OfferStatus status, LocalDateTime createdAt, Long lenderId, String lenderNickname) {
        this.id = id;
        this.amount = amount;
        this.interestRate = interestRate;
        this.termInMonths = termInMonths;
        this.status = status;
        this.createdAt = createdAt;
        this.lenderId = lenderId;
        this.lenderNickname = lenderNickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getLenderId() {
        return lenderId;
    }

    public void setLenderId(Long lenderId) {
        this.lenderId = lenderId;
    }

    public String getLenderNickname() {
        return lenderNickname;
    }

    public void setLenderNickname(String lenderNickname) {
        this.lenderNickname = lenderNickname;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private BigDecimal amount;
        private Double interestRate;
        private Integer termInMonths;
        private OfferStatus status;
        private LocalDateTime createdAt;
        private Long lenderId;
        private String lenderNickname;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder interestRate(Double interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public Builder termInMonths(Integer termInMonths) {
            this.termInMonths = termInMonths;
            return this;
        }

        public Builder status(OfferStatus status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder lenderId(Long lenderId) {
            this.lenderId = lenderId;
            return this;
        }

        public Builder lenderNickname(String lenderNickname) {
            this.lenderNickname = lenderNickname;
            return this;
        }

        public LenderOfferResponse build() {
            return new LenderOfferResponse(id, amount, interestRate, termInMonths, status, createdAt, lenderId, lenderNickname);
        }
    }

    @Override
    public String toString() {
        return "LenderOfferResponse{" +
                "id=" + id +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", termInMonths=" + termInMonths +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", lenderId=" + lenderId +
                ", lenderNickname='" + lenderNickname + '\'' +
                '}';
    }
}