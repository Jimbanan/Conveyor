package com.neoflex.conveyor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j

public class Calculations {

    public BigDecimal getMonthlyInterest(BigDecimal rate) {
        log.info("getMonthlyInterest() - rate: {}", rate);

        BigDecimal monthlyInterest = rate.divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), 5, RoundingMode.HALF_UP);

        log.info("getMonthlyInterest() - Ежемесячный процент: {}", monthlyInterest);

        return monthlyInterest;
    }

    public BigDecimal getMonthlyPayment(BigDecimal rate, BigDecimal totalAmount, Integer term) {
        log.info("getMonthlyPayment() - rate: {}, totalAmount: {}", rate, totalAmount);

        BigDecimal i = getMonthlyInterest(rate);

        BigDecimal monthlyPayment = totalAmount.multiply(i.add(i.divide(i.add(BigDecimal.valueOf(1)).pow(term).subtract(BigDecimal.valueOf(1)), 5, RoundingMode.HALF_UP)));

        log.info("getMonthlyPayment() - Ежемесячная оплата: {}", monthlyPayment);

        return monthlyPayment;
    }

    public BigDecimal getTotalAmount(BigDecimal monthlyPayment, Integer term) {
        log.info("getTotalAmount() - monthlyPayment: {}, term: {}", monthlyPayment, term);

        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(term));

        log.info("getTotalAmount - Итоговая сумма кредита: {}", totalAmount);

        return totalAmount;
    }

    public BigDecimal getInterestPayment(BigDecimal monthlyPayment, BigDecimal totalAmount, Integer term) {
        log.info("getTotalAmount() - monthlyPayment: {}, totalAmount: {}, term: {}", monthlyPayment, totalAmount, term);

        BigDecimal interestPayment = monthlyPayment.subtract(totalAmount.divide(BigDecimal.valueOf(term), 5, RoundingMode.HALF_UP));

        log.info("getInterestPayment - Сумма процентов: {}", interestPayment);

        return interestPayment;
    }

}
