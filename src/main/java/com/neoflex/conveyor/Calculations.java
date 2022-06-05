package com.neoflex.conveyor;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class Calculations {

    public BigDecimal getMonthlyInterest(BigDecimal rate) {

        return rate.divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), 5, RoundingMode.HALF_UP);
    }

    public BigDecimal getMonthlyPayment(BigDecimal rate, BigDecimal totalAmount) {

        BigDecimal i = getMonthlyInterest(rate);

        return totalAmount.multiply(i.add(i.divide(i.add(BigDecimal.valueOf(1)).pow(6).subtract(BigDecimal.valueOf(1)), 5, RoundingMode.HALF_UP)));
    }

    public BigDecimal getTotalAmount(BigDecimal monthlyPayment, Integer term) {

        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }

    public BigDecimal getInterestPayment(BigDecimal monthlyPayment, BigDecimal totalAmount, Integer term) {

        return monthlyPayment.subtract(totalAmount.divide(BigDecimal.valueOf(term), 5, RoundingMode.HALF_UP));
    }

}
