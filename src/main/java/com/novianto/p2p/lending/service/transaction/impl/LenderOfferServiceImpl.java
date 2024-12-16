package com.novianto.p2p.lending.service.transaction.impl;

import com.novianto.p2p.lending.model.LendingOffer;
import com.novianto.p2p.lending.model.Loan;
import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.model.enumType.LoanStatus;
import com.novianto.p2p.lending.model.enumType.OfferStatus;
import com.novianto.p2p.lending.model.enumType.TransactionType;
import com.novianto.p2p.lending.payload.request.transaction.LenderOfferRequest;
import com.novianto.p2p.lending.repository.LenderOfferRepository;
import com.novianto.p2p.lending.repository.LoanRepository;
import com.novianto.p2p.lending.service.auth.UserService;
import com.novianto.p2p.lending.service.global.LogService;
import com.novianto.p2p.lending.service.transaction.LenderOfferService;

import com.novianto.p2p.lending.service.transaction.PaymentService;
import com.novianto.p2p.lending.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LenderOfferServiceImpl implements LenderOfferService {

    private final LenderOfferRepository lenderOfferRepository;
    private final LogService logService;
    private final LoanRepository loanRepository;
    private final UserService userService;
    private final PaymentService paymentService;
    private final TransactionService transactionService;

    @Autowired
    public LenderOfferServiceImpl(LenderOfferRepository lenderOfferRepository, LogService logService, LoanRepository loanRepository, UserService userService, PaymentService paymentService, TransactionService transactionService) {
        this.lenderOfferRepository = lenderOfferRepository;
        this.logService = logService;
        this.loanRepository = loanRepository;
        this.userService = userService;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
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

    @Override
    public List<LendingOffer> getActiveLenderOffers() {
        List<LendingOffer> activeOffers = lenderOfferRepository.findByStatus(OfferStatus.ACTIVE);
        logService.log("INFO", "LenderOfferService", "Diterima " + activeOffers.size() + " penawaran aktif dari pendarat.", Thread.currentThread().getName());
        return activeOffers;
    }

    @Override
    @Transactional
    public Loan acceptLenderOffer(User borrower, Long offerId) {
        LendingOffer offer = lenderOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Tawaran pemberi pinjaman tidak ditemukan"));
        if (offer.getStatus() != OfferStatus.ACTIVE) {
            throw new RuntimeException("Penawaran pemberi pinjaman tidak aktif");
        }
        if (offer.getLender().getBalance().compareTo(offer.getAmount()) < 0) {
            throw new RuntimeException("Pemberi pinjaman tidak memiliki cukup dana untuk mengeluarkan pinjaman");
        }
        offer.setStatus(OfferStatus.ACCEPTED);
        lenderOfferRepository.save(offer);

        Loan loan = Loan.builder()
                .borrower(borrower)
                .lender(offer.getLender())
                .amount(offer.getAmount())
                .interestRate(offer.getInterestRate())
                .termInMonths(offer.getTermInMonths())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(offer.getTermInMonths()))
                .status(LoanStatus.ACTIVE)
                .build();

        Loan savedLoan = loanRepository.save(loan);
        paymentService.generatePaymentSchedule(savedLoan);
        transactionService.createTransaction(savedLoan, offer.getLender(), borrower, offer.getAmount(), TransactionType.LOAN_ISSUED);

        userService.updateUserBalance(offer.getLender(), offer.getAmount().negate());
        userService.updateUserBalance(borrower, offer.getAmount());

        logService.log("INFO", "LenderOfferService", "Peminjam " + borrower.getNickname() + " menerima tawaran itu ID=" + offer.getId() + ", pinjaman tercipta ID=" + savedLoan.getId(), Thread.currentThread().getName());

        return savedLoan;
    }
}
