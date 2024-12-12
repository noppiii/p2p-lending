package com.novianto.p2p.lending.model;

import com.novianto.p2p.lending.model.enumType.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrowing_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowingRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private User borrower;

    @Column(name = "desired_amount", nullable = false)
    private BigDecimal desiredAmount;

    @Column(name = "max_interest_rate", nullable = false)
    private Double maxInterestRate;

    @Column(name = "desired_term_in_months", nullable = false)
    private Integer desiredTermInMonths;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "matched_offer_id")
    private LendingOffer matchedOffer;
}


