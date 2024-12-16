package com.novianto.p2p.lending.repository;

import com.novianto.p2p.lending.model.LendingOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LenderOfferRepository extends JpaRepository<LendingOffer, Long> {
}
