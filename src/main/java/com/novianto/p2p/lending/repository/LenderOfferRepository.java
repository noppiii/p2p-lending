package com.novianto.p2p.lending.repository;

import com.novianto.p2p.lending.model.LendingOffer;
import com.novianto.p2p.lending.model.enumType.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LenderOfferRepository extends JpaRepository<LendingOffer, Long> {
    List<LendingOffer> findByStatus(OfferStatus status);
}
