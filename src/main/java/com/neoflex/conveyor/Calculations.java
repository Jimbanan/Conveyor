package com.neoflex.conveyor;

import java.math.BigDecimal;

public class Calculations {

    public static BigDecimal getMonthlyPayment(BigDecimal rate, BigDecimal totalAmount) {

        BigDecimal i = rate.divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), 5, 4);

        BigDecimal monthlyPayment = totalAmount.multiply(i.add(i.divide(i.add(BigDecimal.valueOf(1)).pow(6).subtract(BigDecimal.valueOf(1)), 5, 4)));

        return monthlyPayment;
    }

    public static BigDecimal getTotalAmount(BigDecimal monthlyPayment, Integer term) {

        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(term));

        return totalAmount;
    }

}
