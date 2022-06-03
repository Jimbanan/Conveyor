package com.neoflex.conveyor.services;

import com.neoflex.conveyor.DTO.*;
import com.neoflex.conveyor.enums.Gender;
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

        //TODO УДАЛИТЬ
        ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
        scoringDataDTO.setBirthdate(loanApplicationRequestDTO.getBirthdate());
        scoringDataDTO.setGender(Gender.MALE);
        ageAndGenderVerification(scoringDataDTO);
        //TODO УДАЛИТЬ

        //TODO Наполнить класс Calculations методами и расчетами для корректного создания объектов
        return LoanOfferDTO.builder()
                .applicationId(new Long((long) (Math.random() * 10)))
                .requestedAmount(loanApplicationRequestDTO.getAmount()) //Готово
                .totalAmount(totalAmount)//Готово
                .term(loanApplicationRequestDTO.getTerm()) //Готово
                .monthlyPayment(new BigDecimal(Math.random() * 3000))
                .rate(rate) //Готово
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();
    }

    @Override
    public CreditDTO loanCalculation(ScoringDataDTO scoringDataDTO) { //TODO Доделать!

        BigDecimal rate = BaseRate;
        BigDecimal amount = scoringDataDTO.getAmount();

        if (scoringDataDTO.getIsSalaryClient()) {
            rate = rate.subtract(BigDecimal.valueOf(1));
        }

        if (scoringDataDTO.getIsInsuranceEnabled()) {
            amount = amount.add(scoringDataDTO.getAmount().multiply(BigDecimal.valueOf(0.05)));
            rate = rate.subtract(BigDecimal.valueOf(3));
        }

        return null;
    }

    private Integer ageAndGenderVerification(ScoringDataDTO scoringDataDTO) {

        Integer rateUpdate = new Integer(0);

        LocalDate now = LocalDate.now();

        if(scoringDataDTO.getGender() == Gender.MALE
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) >= 30)
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) <=55)){
            rateUpdate = -3;
//            rateUpdate = rateUpdate.subtract(BigDecimal.valueOf(-3));
        }

        if(scoringDataDTO.getGender() == Gender.WOMAN
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) >= 35)
                && (ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), now) <=60)){
            rateUpdate = -3;
//            rateUpdate = rateUpdate.subtract(BigDecimal.valueOf(-3));
        }

        if(scoringDataDTO.getGender() == Gender.NOT_BINARY){
            rateUpdate = 3;
//            rateUpdate = rateUpdate.subtract(BigDecimal.valueOf(3));
        }

        System.out.println(rateUpdate);
        return rateUpdate;

    }

    private List<PaymentScheduleElement> getPaymentScheduleElement(BigDecimal rate, Integer term, BigDecimal monthlyPayment, BigDecimal amount){

        //18.29

        return null;
    }


}
