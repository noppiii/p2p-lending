package com.novianto.p2p.lending.service.transaction;

import com.novianto.p2p.lending.model.LendingOffer;
import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.model.enumType.OfferStatus;
import com.novianto.p2p.lending.payload.request.transaction.LenderOfferRequest;
import com.novianto.p2p.lending.repository.LenderOfferRepository;
import com.novianto.p2p.lending.service.global.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LenderOfferServiceImpl implements LenderOfferService {

    private final LenderOfferRepository lenderOfferRepository;
    private final LogService logService;

    @Autowired
    public LenderOfferServiceImpl(LenderOfferRepository lenderOfferRepository, LogService logService) {
        this.lenderOfferRepository = lenderOfferRepository;
        this.logService = logService;
    }

    @Override
    public LendingOffer createLenderOffer(User lender, LenderOfferRequest offerRequest) {
        LendingOffer offer = LendingOffer.builder()
                .amount(offerRequest.getAmount())
                .interestRate(offerRequest.getInterestRate())
                .termInMonths(offerRequest.getTermInMonths())
                .status(OfferStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .lender(lender)
                .build();

        LendingOffer savedOffer = lenderOfferRepository.save(offer);

        logService.log("INFO", "LenderOfferService", "Penawaran baru dibuat: ID=" + savedOffer.getId() + " dari Pendarat=" + lender.getNickname(), Thread.currentThread().getName());

        return savedOffer;
    }
}
