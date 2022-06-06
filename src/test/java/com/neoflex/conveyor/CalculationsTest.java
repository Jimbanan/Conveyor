package com.neoflex.conveyor;

import com.neoflex.conveyor.services.ConveyorServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

class CalculationsTest {

    Calculations calculations = new Calculations();

    @Test
    void getMonthlyInterest() {
        System.out.println("Ежемесячный процент - Ставка 20%");
        Assertions.assertEquals(BigDecimal.valueOf(0.01667), calculations.getMonthlyInterest(BigDecimal.valueOf(20)));

        System.out.println("Ежемесячный процент - Ставка 14%");
        Assertions.assertEquals(BigDecimal.valueOf(0.01167), calculations.getMonthlyInterest(BigDecimal.valueOf(14)));

    }

    @Test
    void getMonthlyPayment() {
        System.out.println("Ежемесячный платеж");
        Assertions.assertEquals(BigDecimal.valueOf(1765.20000).setScale(5), calculations.getMonthlyPayment(BigDecimal.valueOf(20), BigDecimal.valueOf(10000), 6));

        Assertions.assertEquals(BigDecimal.valueOf(44895.00000).setScale(5), calculations.getMonthlyPayment(BigDecimal.valueOf(14), BigDecimal.valueOf(500000), 12));

    }

    @Test
    void getTotalAmount() {

        System.out.println("Итоговая сумма");

        Assertions.assertEquals(BigDecimal.valueOf(10591.20000).setScale(5), calculations.getTotalAmount(calculations.getMonthlyPayment(BigDecimal.valueOf(20), BigDecimal.valueOf(10000), 6), 6));

        Assertions.assertEquals(BigDecimal.valueOf(11116.80000).setScale(5), calculations.getTotalAmount(calculations.getMonthlyPayment(BigDecimal.valueOf(20), BigDecimal.valueOf(10000), 12), 12));

    }

    @Test
    void getInterestPayment() {
        System.out.println("Сумма процентов");

        Assertions.assertEquals(BigDecimal.valueOf(98.53333).setScale(5), calculations.getInterestPayment(calculations.getMonthlyPayment((BigDecimal.valueOf(20)), BigDecimal.valueOf(10000), 6), BigDecimal.valueOf(10000), 6));

        Assertions.assertEquals(BigDecimal.valueOf(3228.33333).setScale(5), calculations.getInterestPayment(calculations.getMonthlyPayment((BigDecimal.valueOf(14)), BigDecimal.valueOf(500000), 12), BigDecimal.valueOf(500000), 12));

    }
}