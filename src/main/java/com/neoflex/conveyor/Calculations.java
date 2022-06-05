package com.neoflex.conveyor;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j

public class Calculations {

    public BigDecimal getMonthlyInterest(BigDecimal rate) {

        BigDecimal monthlyInterest = rate.divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), 5, RoundingMode.HALF_UP);

        log.info("getMonthlyInterest() - Ежемесячный процент: {}", monthlyInterest);

        return monthlyInterest;
    }

    public BigDecimal getMonthlyPayment(BigDecimal rate, BigDecimal totalAmount) {

        BigDecimal i = getMonthlyInterest(rate);

        BigDecimal monthlyPayment = totalAmount.multiply(i.add(i.divide(i.add(BigDecimal.valueOf(1)).pow(6).subtract(BigDecimal.valueOf(1)), 5, RoundingMode.HALF_UP)));

        log.info("getMonthlyPayment() - Ежемесячная оплата: {}", monthlyPayment);

        return monthlyPayment;
    }

    public BigDecimal getTotalAmount(BigDecimal monthlyPayment, Integer term) {

        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(term));

        log.info("getTotalAmount - Итоговая сумма кредита: {}", totalAmount);

        return totalAmount;
    }

    public BigDecimal getInterestPayment(BigDecimal monthlyPayment, BigDecimal totalAmount, Integer term) {

        BigDecimal interestPayment = monthlyPayment.subtract(totalAmount.divide(BigDecimal.valueOf(term), 5, RoundingMode.HALF_UP));

        log.info("getInterestPayment - Сумма процентов: {}", interestPayment);

        return interestPayment;
    }

}
