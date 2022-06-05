package com.neoflex.conveyor;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Calculations {

    public BigDecimal getMonthlyInterest(BigDecimal rate, BigDecimal totalAmount) {

        return rate.divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), 5, 4);
    }

    public BigDecimal getMonthlyPayment(BigDecimal rate, BigDecimal totalAmount) {

        BigDecimal i = getMonthlyInterest(rate, totalAmount);

        BigDecimal monthlyPayment = totalAmount.multiply(i.add(i.divide(i.add(BigDecimal.valueOf(1)).pow(6).subtract(BigDecimal.valueOf(1)), 5, 4)));

        return monthlyPayment;
    }

    public BigDecimal getTotalAmount(BigDecimal monthlyPayment, Integer term) {

        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(term));

        return totalAmount;
    }

    public BigDecimal getInterestPayment(BigDecimal monthlyPayment, BigDecimal totalAmount, Integer term) {

        return monthlyPayment.subtract(totalAmount.divide(BigDecimal.valueOf(term), 5, 4));
    }

}
