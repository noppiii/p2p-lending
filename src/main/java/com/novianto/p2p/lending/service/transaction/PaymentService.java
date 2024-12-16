package com.novianto.p2p.lending.service.transaction;

import com.novianto.p2p.lending.model.Loan;
import com.novianto.p2p.lending.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    List<Payment> generatePaymentSchedule(Loan loan);

    BigDecimal calculateAnnuityPayment(BigDecimal principal, BigDecimal monthlyRate, int totalPayments);
}
