package com.novianto.p2p.lending.service.transaction.impl;

import com.novianto.p2p.lending.model.Loan;
import com.novianto.p2p.lending.model.Payment;
import com.novianto.p2p.lending.repository.PaymentRepository;
import com.novianto.p2p.lending.service.global.LogService;
import com.novianto.p2p.lending.service.transaction.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final LogService logService;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, LogService logService) {
        this.paymentRepository = paymentRepository;
        this.logService = logService;
    }

    @Override
    public List<Payment> generatePaymentSchedule(Loan loan) {
        List<Payment> paymentSchedule = new ArrayList<>();
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(loan.getInterestRate() / 100 / 12);
        int totalPayment = loan.getTermInMonths();
        BigDecimal loanAmount = loan.getAmount();

        BigDecimal monthlyPaymentAmount = calculateAnnuityPayment(loanAmount, monthlyInterestRate, totalPayment);
        LocalDateTime paymentDate = loan.getStartDate().plusMonths(1);

        for (int i = 0; i < totalPayment; i++) {
            BigDecimal interestAmount = loanAmount.multiply(monthlyInterestRate);
            BigDecimal principalAmount = monthlyPaymentAmount.subtract(interestAmount);

            Payment payment = Payment.builder()
                    .loan(loan)
                    .paymentDate(paymentDate)
                    .amount(interestAmount)
                    .principalAmount(principalAmount)
                    .isPaid(false)
                    .notificationSent(false)
                    .build();

            paymentSchedule.add(payment);
            loanAmount = loanAmount.subtract(principalAmount);
            paymentDate.plusMonths(1);
        }

        List<Payment> savedPayment = paymentRepository.saveAll(paymentSchedule);

        logService.log("INFO", "PaymentService", "Jadwal pembayaran pinjaman telah dibuat ID=" + loan.getId(),
                Thread.currentThread().getName());

        return savedPayment;
    }

    @Override
    public BigDecimal calculateAnnuityPayment(BigDecimal principal, BigDecimal monthlyRate, int totalPayments) {
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);
        BigDecimal pow = onePlusRate.pow(totalPayments, MathContext.DECIMAL128);
        BigDecimal numerator = principal.multiply(monthlyRate).multiply(pow);
        BigDecimal denominator = pow.subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }


}
