package com.novianto.p2p.lending.model;

import com.novianto.p2p.lending.model.enumType.OfferStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lender_offers")
public class LendingOffer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lender_id", nullable = false)
    private User lender;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

    @Column(name = "term_in_months", nullable = false)
    private Integer termInMonths;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferStatus status = OfferStatus.ACTIVE;

    public LendingOffer() {
    }

    public LendingOffer(Long id, User lender, BigDecimal amount, Double interestRate, Integer termInMonths,
                        LocalDateTime createdAt, OfferStatus status) {
        this.id = id;
        this.lender = lender;
        this.amount = amount;
        this.interestRate = interestRate;
        this.termInMonths = termInMonths;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getLender() {
        return lender;
    }

    public void setLender(User lender) {
        this.lender = lender;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public static class Builder {
        private Long id;
        private User lender;
        private BigDecimal amount;
        private Double interestRate;
        private Integer termInMonths;
        private LocalDateTime createdAt;
        private OfferStatus status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder lender(User lender) {
            this.lender = lender;
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

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder status(OfferStatus status) {
            this.status = status;
            return this;
        }

        public LendingOffer build() {
            return new LendingOffer(id, lender, amount, interestRate, termInMonths, createdAt, status);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "LendingOffer{" +
                "id=" + id +
                ", lender=" + lender +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", termInMonths=" + termInMonths +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}