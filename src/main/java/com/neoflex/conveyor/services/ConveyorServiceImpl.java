package com.neoflex.conveyor.services;

import com.neoflex.conveyor.Calculations;
import com.neoflex.conveyor.dto.*;
import com.neoflex.conveyor.exceptions.ScoringException;
import com.neoflex.conveyor.enums.EmploymentStatus;
import com.neoflex.conveyor.enums.Genders;
import com.neoflex.conveyor.enums.MaritalStatus;
import com.neoflex.conveyor.enums.Position;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConveyorServiceImpl implements ConveyorService {

    @Value("${credit.baseRate}")
    BigDecimal BaseRate;

    @Autowired
    Calculations calculations;

    @Override
    public List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("getOffers() - loanApplicationRequestDTO: {}", loanApplicationRequestDTO);

        List<LoanOfferDTO> loanOfferList = new ArrayList<>();

        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, true, true));
        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, true, false));
        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, false, false));
        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, false, true));

        log.info("getOffers() - List<LoanOfferDTO>: {}", loanOfferList);

        return loanOfferList;
    }

    @Override
    public LoanOfferDTO formationOfOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        log.info("formationOfOffers() - loanApplicationRequestDTO: {}, isInsuranceEnabled: {}, isSalaryClient: {}", loanApplicationRequestDTO, isInsuranceEnabled, isSalaryClient);

        BigDecimal rate = BaseRate;
        BigDecimal totalAmount = loanApplicationRequestDTO.getAmount();

        if (isSalaryClient) {
            rate = rate.subtract(BigDecimal.valueOf(1));

            log.info("formationOfOffers() - Клиент является Зарплатным: Уменьшение ставки на 1 Ставка: {}", rate);
        }

        if (isInsuranceEnabled) {
            totalAmount = loanApplicationRequestDTO.getAmount()
                    .add(loanApplicationRequestDTO.getAmount().multiply(BigDecimal.valueOf(0.05)));

            rate = rate.subtract(BigDecimal.valueOf(3));

            log.info("formationOfOffers() - Включена страховка: Уменьшение ставки на 3 - Увеличение итоговой суммы Ставка {}", rate);
        }

        log.info("formationOfOffers() - Предлагаемая ставка: {}", rate);

        BigDecimal monthlyPayment = calculations.getMonthlyPayment(rate, totalAmount, loanApplicationRequestDTO.getTerm());

        log.info("formationOfOffers() - Ежемесячная плата: {}", monthlyPayment);

        return LoanOfferDTO.builder()
                .applicationId(loanApplicationRequestDTO.getApplicationId())
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

        log.info("loanCalculation() - scoringDataDTO: {}", scoringDataDTO);

        BigDecimal rate = BaseRate;
        BigDecimal amount = scoringDataDTO.getAmount();
        LocalDate now = LocalDate.now();

        if (scoringDataDTO.getIsSalaryClient()) {
            rate = rate.subtract(BigDecimal.valueOf(1));
            log.info("loanCalculation() - Клиент является Зарплатным: Уменьшение ставки на 1 Ставка: {}", rate);
        }

        if (scoringDataDTO.getIsInsuranceEnabled()) {
            amount = amount.add(scoringDataDTO.getAmount().multiply(BigDecimal.valueOf(0.05)));
            rate = rate.subtract(BigDecimal.valueOf(3));
            log.info("formationOfOffers() - Включена страховка: Уменьшение ставки на 3 - Увеличение итоговой суммы Ставка {}", rate);
        }

        if (scoringDataDTO.getEmployment().getEmploymentStatus() == EmploymentStatus.UNEMPLOYED) {
            log.error("Клиент является Безработным");
            throw new ScoringException("Work Status: Unemployed - Denied");
        } else if (scoringDataDTO.getEmployment().getEmploymentStatus() == EmploymentStatus.SELF_EMPLOYED) {
            rate = rate.add(BigDecimal.valueOf(1));
            log.info("Клиент является Частным предпринимателем. Увеличение ставки на 1 Ставка {}", rate);
        } else if (scoringDataDTO.getEmployment().getEmploymentStatus() == EmploymentStatus.BUSINESS_OWNER) {
            rate = rate.add(BigDecimal.valueOf(3));
            log.info("Клиент является Владельцем бизнеса. Увеличение ставки на 3 Ставка {}", rate);
        }

        if (scoringDataDTO.getEmployment().getPosition() == Position.MIDDLE_MANAGER) {
            rate = rate.subtract(BigDecimal.valueOf(2));
            log.info("Клиент является Средним Менеджером. Уменьшение ставки на 2 Ставка {}", rate);
        } else if (scoringDataDTO.getEmployment().getPosition() == Position.TOP_MANAGER) {
            rate = rate.add(BigDecimal.valueOf(4));
            log.info("Клиент является Топ Менеджером. Увеличение ставки на 4 Ставка {}", rate);
        }

        if (scoringDataDTO.getEmployment().getSalary().multiply(BigDecimal.valueOf(20)).compareTo(scoringDataDTO.getAmount()) < 0) {
            log.error("Запрашиваемая сумма больше 20 окладов");
            throw new ScoringException("Requested amount more than 20 salaries - Denied");
        }

        if (scoringDataDTO.getMaritalStatus() == MaritalStatus.MARRIED_MARRIED) {
            rate = rate.subtract(BigDecimal.valueOf(3));
            log.info("Клиент состоит в браке. Уменьшение ставки на 3 Ставка {}", rate);
        } else if (scoringDataDTO.getMaritalStatus() == MaritalStatus.DIVORCED) {
            rate = rate.add(BigDecimal.valueOf(3));
            log.info("Клиент не состоит в браке. Увеличение ставки на 3 Ставка {}", rate);
        }

        if (scoringDataDTO.getDependentAmount() > 1) {
            rate = rate.add(BigDecimal.valueOf(1));
            log.info("Количество иждивенцев > 1. Увеличение ставки на 1 Ставка {}", rate);
        }

        if (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) < 20) {
            log.error("Возраст клиента меньше 20");
            throw new ScoringException("Age under 20 - Denied");
        } else if (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) > 60) {
            log.error("Возраст клиента больше 60");
            throw new ScoringException("Age over 60 - Denied");
        }

        if (scoringDataDTO.getGender() == Genders.MALE
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) >= 30)
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) <= 55)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
            log.info("Мужчина, возраст от 30 до 55 лет. Уменьшение ставки на 3 Ставка {}", rate);
        } else if (scoringDataDTO.getGender() == Genders.WOMAN
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) >= 35)
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) <= 60)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
            log.info("Женщина, возраст от 35 до 60 лет. Уменьшение ставки на 3 Ставка {}", rate);
        } else if (scoringDataDTO.getGender() == Genders.NOT_BINARY) {
            rate = rate.add(BigDecimal.valueOf(3));
            log.info("Не бинарный. Увеличение ставки на 3 Ставка {}", rate);
        }

        if (scoringDataDTO.getEmployment().getWorkExperienceTotal() < 12) {
            log.error("Общий стаж работы меньше 12 месяцев");
            throw new ScoringException("Total work experience less than 12 months - Denied");
        } else if (scoringDataDTO.getEmployment().getWorkExperienceCurrent() < 3) {
            log.error("Текущий стаж работы меньше 3 месяцев");
            throw new ScoringException("Current work experience less than 3 months - Denied");
        }

        BigDecimal monthlyPayment = calculations.getMonthlyPayment(rate, amount, scoringDataDTO.getTerm());

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

    public List<PaymentScheduleElement> getPaymentScheduleElement(Integer term, BigDecimal monthlyPayment, BigDecimal amount, BigDecimal totalAmount) {
        log.info("getPaymentScheduleElement() - term: {}, monthlyPayment: {}, amount: {}, totalAmount: {}", term, monthlyPayment, amount, totalAmount);

        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<>();
        LocalDate now = LocalDate.now();
        BigDecimal remainingDebt = totalAmount;
        BigDecimal totalPayment = BigDecimal.valueOf(0);

        for (int i = 1; i <= term; i++) {
            PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
            paymentScheduleElement.setNumber(i);
            log.info("getPaymentScheduleElement() - Месяц: {}", i);

            now = now.plusMonths(1);
            paymentScheduleElement.setDate(now);
            log.info("getPaymentScheduleElement() - Дата выплаты: {}", now);

            totalPayment = totalPayment.add(monthlyPayment);
            paymentScheduleElement.setTotalPayment(totalPayment);
            log.info("getPaymentScheduleElement() - Всего выплачено: {}", totalPayment);

            paymentScheduleElement.setInterestPayment(calculations.getInterestPayment(monthlyPayment, amount, term));
            log.info("getPaymentScheduleElement() - Сумма процентов: {}", calculations.getInterestPayment(monthlyPayment, amount, term));

            paymentScheduleElement.setDebtPayment(monthlyPayment);
            log.info("getPaymentScheduleElement() - Сумма оплаты за текущий месяц: {}", monthlyPayment);

            remainingDebt = remainingDebt.subtract(monthlyPayment);
            paymentScheduleElement.setRemainingDebt(remainingDebt);
            log.info("getPaymentScheduleElement() - Оставшаяся сумма: {}", remainingDebt);

            paymentScheduleElementList.add(paymentScheduleElement);
        }

        log.info("getPaymentScheduleElement() - List<PaymentScheduleElement>: {}", paymentScheduleElementList);
        return paymentScheduleElementList;
    }


}
