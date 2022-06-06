package com.neoflex.conveyor.services;

import com.neoflex.conveyor.DTO.*;

import java.math.BigDecimal;
import java.util.List;

public interface ConveyorService {

    List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO);

    LoanOfferDTO formationOfOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Boolean isInsuranceEnabled, Boolean isSalaryClient);

    CreditDTO loanCalculation(ScoringDataDTO scoringDataDTO);

    List<PaymentScheduleElement> getPaymentScheduleElement(Integer term, BigDecimal monthlyPayment, BigDecimal amount, BigDecimal totalAmount);

    }
