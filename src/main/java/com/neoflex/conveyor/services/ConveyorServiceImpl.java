package com.neoflex.conveyor.services;

import com.neoflex.conveyor.Calculations;
import com.neoflex.conveyor.DTO.*;
import com.neoflex.conveyor.Exceptions.ScoringException;
import com.neoflex.conveyor.enums.EmploymentStatus;
import com.neoflex.conveyor.enums.Genders;
import com.neoflex.conveyor.enums.MaritalStatus;
import com.neoflex.conveyor.enums.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConveyorServiceImpl implements ConveyorService {

    @Value("${credit.baseRate}")
    BigDecimal BaseRate;

    @Autowired
    Calculations calculations;

    @Override
    public List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        List<LoanOfferDTO> loanOfferList = new ArrayList<>();

        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, true, true));
        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, true, false));
        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, false, false));
        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, false, true));

        return loanOfferList;
    }

    @Override
    public LoanOfferDTO formationOfOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Boolean isInsuranceEnabled, Boolean isSalaryClient) {

        BigDecimal rate = BaseRate;
        BigDecimal totalAmount = loanApplicationRequestDTO.getAmount();

        if (isSalaryClient) {
            rate = rate.subtract(BigDecimal.valueOf(1));
        }

        if (isInsuranceEnabled) {
            totalAmount = loanApplicationRequestDTO.getAmount()
                    .add(loanApplicationRequestDTO.getAmount().multiply(BigDecimal.valueOf(0.05)));

            rate = rate.subtract(BigDecimal.valueOf(3));
        }

        BigDecimal monthlyPayment = calculations.getMonthlyPayment(rate, totalAmount);

        return LoanOfferDTO.builder()
                .applicationId(1L)
                .requestedAmount(loanApplicationRequestDTO.getAmount())
                .totalAmount(calculations.getTotalAmount(monthlyPayment, loanApplicationRequestDTO.getTerm()))
                .term(loanApplicationRequestDTO.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();
    }

    @Override
    public CreditDTO loanCalculation(ScoringDataDTO scoringDataDTO) {

        BigDecimal rate = BaseRate;
        BigDecimal amount = scoringDataDTO.getAmount();
        LocalDate now = LocalDate.now();

        if (scoringDataDTO.getIsSalaryClient()) {
            rate = rate.subtract(BigDecimal.valueOf(1));
        }

        if (scoringDataDTO.getIsInsuranceEnabled()) {
            amount = amount.add(scoringDataDTO.getAmount().multiply(BigDecimal.valueOf(0.05)));
            rate = rate.subtract(BigDecimal.valueOf(3));
        }

        if (scoringDataDTO.getEmployment().getEmploymentStatus() == EmploymentStatus.UNEMPLOYED) {
            throw new ScoringException("Work Status: Unemployed - Denied");
        } else if (scoringDataDTO.getEmployment().getEmploymentStatus() == EmploymentStatus.SELF_EMPLOYED) {
            rate = rate.add(BigDecimal.valueOf(1));
        } else if (scoringDataDTO.getEmployment().getEmploymentStatus() == EmploymentStatus.BUSINESS_OWNER) {
            rate = rate.add(BigDecimal.valueOf(3));
        }

        if (scoringDataDTO.getEmployment().getPosition() == Position.MIDDLE_MANAGER) {
            rate = rate.subtract(BigDecimal.valueOf(2));
        } else if (scoringDataDTO.getEmployment().getPosition() == Position.TOP_MANAGER) {
            rate = rate.add(BigDecimal.valueOf(4));
        }

        if (scoringDataDTO.getEmployment().getSalary().multiply(BigDecimal.valueOf(20)).compareTo(scoringDataDTO.getAmount()) < 0) {
            throw new ScoringException("Requested amount more than 20 salaries - Denied");
        }

        if (scoringDataDTO.getMaritalStatus() == MaritalStatus.MARRIED_MARRIED) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (scoringDataDTO.getMaritalStatus() == MaritalStatus.DIVORCED) {
            rate = rate.add(BigDecimal.valueOf(3));
        }

        if (scoringDataDTO.getDependentAmount() > 1) {
            rate = rate.add(BigDecimal.valueOf(1));
        }

        if (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) < 20) {
            throw new ScoringException("Age under 20 - Denied");
        } else if (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) > 60) {
            throw new ScoringException("Age over 60 - Denied");
        }

        if (scoringDataDTO.getGender() == Genders.MALE
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) >= 30)
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) <= 55)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (scoringDataDTO.getGender() == Genders.WOMAN
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) >= 35)
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) <= 60)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (scoringDataDTO.getGender() == Genders.NOT_BINARY) {
            rate = rate.add(BigDecimal.valueOf(3));
        }

        if (scoringDataDTO.getEmployment().getWorkExperienceTotal() < 12) {
            throw new ScoringException("Total work experience less than 12 months - Denied");
        } else if (scoringDataDTO.getEmployment().getWorkExperienceCurrent() < 3) {
            throw new ScoringException("Current work experience less than 3 months - Denied");
        }

        BigDecimal monthlyPayment = calculations.getMonthlyPayment(rate, amount);
        return CreditDTO.builder()
                .amount(amount)
                .term(scoringDataDTO.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(calculations.getTotalAmount(monthlyPayment, scoringDataDTO.getTerm()))
                .isInsuranceEnabled(scoringDataDTO.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDTO.getIsSalaryClient())
                .paymentSchedule(getPaymentScheduleElement(scoringDataDTO.getTerm(), monthlyPayment, amount, calculations.getTotalAmount(monthlyPayment, scoringDataDTO.getTerm())))
                .build();
    }

    private List<PaymentScheduleElement> getPaymentScheduleElement(Integer term, BigDecimal monthlyPayment, BigDecimal amount, BigDecimal totalAmount) {

        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<>();
        LocalDate now = LocalDate.now();
        BigDecimal remainingDebt = totalAmount;
        BigDecimal totalPayment = BigDecimal.valueOf(0);

        for (int i = 1; i <= term; i++) {
            PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
            paymentScheduleElement.setNumber(i);

            now = now.plusMonths(1);
            paymentScheduleElement.setDate(now);

            totalPayment = totalPayment.add(monthlyPayment);
            paymentScheduleElement.setTotalPayment(totalPayment);

            paymentScheduleElement.setInterestPayment(calculations.getInterestPayment(monthlyPayment, amount, term));

            paymentScheduleElement.setDebtPayment(monthlyPayment);

            remainingDebt = remainingDebt.subtract(monthlyPayment);
            paymentScheduleElement.setRemainingDebt(remainingDebt);

            paymentScheduleElementList.add(paymentScheduleElement);
        }

        return paymentScheduleElementList;
    }


}
