package com.neoflex.conveyor.services;

import com.neoflex.conveyor.Calculations;
import com.neoflex.conveyor.DTO.*;
import com.neoflex.conveyor.Exceptions.ScoringException;
import com.neoflex.conveyor.enums.EmploymentStatus;
import com.neoflex.conveyor.enums.Genders;
import com.neoflex.conveyor.enums.MaritalStatus;
import com.neoflex.conveyor.enums.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
class ConveyorServiceImplTest {

    @Spy
    ConveyorServiceImpl conveyorService = new ConveyorServiceImpl();

    @Spy
    Calculations calculations = new Calculations();

    @Test
    void getOffers() {

        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO();

        loanApplicationRequestDTO.setAmount(BigDecimal.valueOf(10000));
        loanApplicationRequestDTO.setTerm(6);
        loanApplicationRequestDTO.setFirstName("Николай");
        loanApplicationRequestDTO.setLastName("Козьяков");
        loanApplicationRequestDTO.setMiddleName("Николаевич");
        loanApplicationRequestDTO.setEmail("uservice371@mail.ru");
        loanApplicationRequestDTO.setBirthdate(LocalDate.of(1991, 9, 26));
        loanApplicationRequestDTO.setPassportSeries("1234");
        loanApplicationRequestDTO.setPassportNumber("123456");

        List<LoanOfferDTO> loanOfferList = new ArrayList<>();

        LoanOfferDTO loanOfferDTO1 = new LoanOfferDTO();
        loanOfferDTO1.setApplicationId(1L);
        loanOfferDTO1.setRequestedAmount(BigDecimal.valueOf(10000));
        loanOfferDTO1.setTotalAmount(BigDecimal.valueOf(10995.3900000).setScale(7));
        loanOfferDTO1.setTerm(6);
        loanOfferDTO1.setMonthlyPayment(BigDecimal.valueOf(1832.5650000).setScale(7));
        loanOfferDTO1.setRate(BigDecimal.valueOf(16));
        loanOfferDTO1.setIsInsuranceEnabled(true);
        loanOfferDTO1.setIsSalaryClient(true);


        loanOfferList.add(loanOfferDTO1);

        LoanOfferDTO loanOfferDTO2 = new LoanOfferDTO();
        loanOfferDTO2.setApplicationId(1L);
        loanOfferDTO2.setRequestedAmount(BigDecimal.valueOf(10000));
        loanOfferDTO2.setTotalAmount(BigDecimal.valueOf(11026.8900000).setScale(7));
        loanOfferDTO2.setTerm(6);
        loanOfferDTO2.setMonthlyPayment(BigDecimal.valueOf(1837.8150000).setScale(7));
        loanOfferDTO2.setRate(BigDecimal.valueOf(17));
        loanOfferDTO2.setIsInsuranceEnabled(true);
        loanOfferDTO2.setIsSalaryClient(false);

        loanOfferList.add(loanOfferDTO2);

        LoanOfferDTO loanOfferDTO3 = new LoanOfferDTO();
        loanOfferDTO3.setApplicationId(1L);
        loanOfferDTO3.setRequestedAmount(BigDecimal.valueOf(10000));
        loanOfferDTO3.setTotalAmount(BigDecimal.valueOf(10591.20000).setScale(5));
        loanOfferDTO3.setTerm(6);
        loanOfferDTO3.setMonthlyPayment(BigDecimal.valueOf(1765.20000).setScale(5));
        loanOfferDTO3.setRate(BigDecimal.valueOf(20));
        loanOfferDTO3.setIsInsuranceEnabled(false);
        loanOfferDTO3.setIsSalaryClient(false);

        loanOfferList.add(loanOfferDTO3);

        LoanOfferDTO loanOfferDTO4 = new LoanOfferDTO();
        loanOfferDTO4.setApplicationId(1L);
        loanOfferDTO4.setRequestedAmount(BigDecimal.valueOf(10000));
        loanOfferDTO4.setTotalAmount(BigDecimal.valueOf(10561.20000).setScale(5));
        loanOfferDTO4.setTerm(6);
        loanOfferDTO4.setMonthlyPayment(BigDecimal.valueOf(1760.20000).setScale(5));
        loanOfferDTO4.setRate(BigDecimal.valueOf(19));
        loanOfferDTO4.setIsInsuranceEnabled(false);
        loanOfferDTO4.setIsSalaryClient(true);

        loanOfferList.add(loanOfferDTO4);

        ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
        ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

        List<LoanOfferDTO> loanOfferListTest = conveyorService.getOffers(loanApplicationRequestDTO);

        Assertions.assertEquals(loanOfferList, loanOfferListTest);

    }

    @Test
    void formationOfOffers() {

        ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
        ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO();

        loanApplicationRequestDTO.setAmount(BigDecimal.valueOf(10000));
        loanApplicationRequestDTO.setTerm(6);
        loanApplicationRequestDTO.setFirstName("Николай");
        loanApplicationRequestDTO.setLastName("Козьяков");
        loanApplicationRequestDTO.setMiddleName("Николаевич");
        loanApplicationRequestDTO.setEmail("uservice371@mail.ru");
        loanApplicationRequestDTO.setBirthdate(LocalDate.of(1991, 9, 26));
        loanApplicationRequestDTO.setPassportSeries("1234");
        loanApplicationRequestDTO.setPassportNumber("123456");

        LoanOfferDTO loanOfferDTO1 = new LoanOfferDTO();
        loanOfferDTO1.setApplicationId(1L);
        loanOfferDTO1.setRequestedAmount(BigDecimal.valueOf(10000));
        loanOfferDTO1.setTotalAmount(BigDecimal.valueOf(10995.3900000).setScale(7));
        loanOfferDTO1.setTerm(6);
        loanOfferDTO1.setMonthlyPayment(BigDecimal.valueOf(1832.5650000).setScale(7));
        loanOfferDTO1.setRate(BigDecimal.valueOf(16));
        loanOfferDTO1.setIsInsuranceEnabled(true);
        loanOfferDTO1.setIsSalaryClient(true);

        Assertions.assertEquals(loanOfferDTO1, conveyorService.formationOfOffers(loanApplicationRequestDTO, true, true));

        LoanOfferDTO loanOfferDTO2 = new LoanOfferDTO();
        loanOfferDTO2.setApplicationId(1L);
        loanOfferDTO2.setRequestedAmount(BigDecimal.valueOf(10000));
        loanOfferDTO2.setTotalAmount(BigDecimal.valueOf(11026.8900000).setScale(7));
        loanOfferDTO2.setTerm(6);
        loanOfferDTO2.setMonthlyPayment(BigDecimal.valueOf(1837.8150000).setScale(7));
        loanOfferDTO2.setRate(BigDecimal.valueOf(17));
        loanOfferDTO2.setIsInsuranceEnabled(true);
        loanOfferDTO2.setIsSalaryClient(false);

        Assertions.assertEquals(loanOfferDTO2, conveyorService.formationOfOffers(loanApplicationRequestDTO, true, false));
        LoanOfferDTO loanOfferDTO3 = new LoanOfferDTO();
        loanOfferDTO3.setApplicationId(1L);
        loanOfferDTO3.setRequestedAmount(BigDecimal.valueOf(10000));
        loanOfferDTO3.setTotalAmount(BigDecimal.valueOf(10591.20000).setScale(5));
        loanOfferDTO3.setTerm(6);
        loanOfferDTO3.setMonthlyPayment(BigDecimal.valueOf(1765.20000).setScale(5));
        loanOfferDTO3.setRate(BigDecimal.valueOf(20));
        loanOfferDTO3.setIsInsuranceEnabled(false);
        loanOfferDTO3.setIsSalaryClient(false);

        Assertions.assertEquals(loanOfferDTO3, conveyorService.formationOfOffers(loanApplicationRequestDTO, false, false));

        LoanOfferDTO loanOfferDTO4 = new LoanOfferDTO();
        loanOfferDTO4.setApplicationId(1L);
        loanOfferDTO4.setRequestedAmount(BigDecimal.valueOf(10000));
        loanOfferDTO4.setTotalAmount(BigDecimal.valueOf(10561.20000).setScale(5));
        loanOfferDTO4.setTerm(6);
        loanOfferDTO4.setMonthlyPayment(BigDecimal.valueOf(1760.20000).setScale(5));
        loanOfferDTO4.setRate(BigDecimal.valueOf(19));
        loanOfferDTO4.setIsInsuranceEnabled(false);
        loanOfferDTO4.setIsSalaryClient(true);

        Assertions.assertEquals(loanOfferDTO4, conveyorService.formationOfOffers(loanApplicationRequestDTO, false, true));

    }

    @Test
    void loanCalculation() {
        ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
        ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

        EmploymentDTO employment1 = new EmploymentDTO();
        employment1.setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        employment1.setEmployerINN("7727563778");
        employment1.setSalary(BigDecimal.valueOf(10000));
        employment1.setPosition(Position.MIDDLE_MANAGER);
        employment1.setWorkExperienceTotal(30);
        employment1.setWorkExperienceCurrent(5);

        ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
        scoringDataDTO.setAmount(BigDecimal.valueOf(200000));
        scoringDataDTO.setTerm(2);
        scoringDataDTO.setFirstName("Николай");
        scoringDataDTO.setLastName("Козьяков");
        scoringDataDTO.setMiddleName("Николаевич");
        scoringDataDTO.setGender(Genders.MALE);
        scoringDataDTO.setBirthdate(LocalDate.of(1980, 9, 29));
        scoringDataDTO.setPassportSeries("1234");
        scoringDataDTO.setPassportNumber("123456");
        scoringDataDTO.setPassportIssueDate(LocalDate.of(2002, 9, 29));
        scoringDataDTO.setPassportIssueBranch("Улица Пушкина - Дом Колотушкина");
        scoringDataDTO.setMaritalStatus(MaritalStatus.MARRIED_MARRIED);
        scoringDataDTO.setDependentAmount(1);
        scoringDataDTO.setEmployment(employment1);
        scoringDataDTO.setAccount("40817810099910004312");
        scoringDataDTO.setIsInsuranceEnabled(false);
        scoringDataDTO.setIsSalaryClient(true);


        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        PaymentScheduleElement paymentScheduleElement1 = new PaymentScheduleElement();
        paymentScheduleElement1.setNumber(1);
        paymentScheduleElement1.setDate(LocalDate.of(2022, 7, 6));
        paymentScheduleElement1.setTotalPayment(BigDecimal.valueOf(101754.00000).setScale(5));
        paymentScheduleElement1.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
        paymentScheduleElement1.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
        paymentScheduleElement1.setRemainingDebt(BigDecimal.valueOf(101754.00000).setScale(5));

        PaymentScheduleElement paymentScheduleElement2 = new PaymentScheduleElement();
        paymentScheduleElement2.setNumber(2);
        paymentScheduleElement2.setDate(LocalDate.of(2022, 8, 6));
        paymentScheduleElement2.setTotalPayment(BigDecimal.valueOf(203508.00000).setScale(5));
        paymentScheduleElement2.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
        paymentScheduleElement2.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
        paymentScheduleElement2.setRemainingDebt(BigDecimal.valueOf(0.00000).setScale(5));

        paymentSchedule.add(paymentScheduleElement1);
        paymentSchedule.add(paymentScheduleElement2);

        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setAmount(BigDecimal.valueOf(200000));
        creditDTO.setTerm(2);
        creditDTO.setMonthlyPayment(BigDecimal.valueOf(101754.00000).setScale(5));
        creditDTO.setRate(BigDecimal.valueOf(14));
        creditDTO.setPsk(BigDecimal.valueOf(203508.00000).setScale(5));
        creditDTO.setIsInsuranceEnabled(false);
        creditDTO.setIsSalaryClient(true);
        creditDTO.setPaymentSchedule(paymentSchedule);


        Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));

        EmploymentDTO employment2 = new EmploymentDTO();
        employment2.setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);
        employment2.setEmployerINN("7727563778");
        employment2.setSalary(BigDecimal.valueOf(10000));
        employment2.setPosition(Position.TOP_MANAGER);
        employment2.setWorkExperienceTotal(30);
        employment2.setWorkExperienceCurrent(5);

        scoringDataDTO.setAmount(BigDecimal.valueOf(200000));
        scoringDataDTO.setTerm(2);
        scoringDataDTO.setFirstName("Зинаида");
        scoringDataDTO.setLastName("Занаииииииида");
        scoringDataDTO.setMiddleName("Зинаидовна");
        scoringDataDTO.setGender(Genders.WOMAN);
        scoringDataDTO.setBirthdate(LocalDate.of(1980, 9, 29));
        scoringDataDTO.setPassportSeries("1234");
        scoringDataDTO.setPassportNumber("123456");
        scoringDataDTO.setPassportIssueDate(LocalDate.of(2002, 9, 29));
        scoringDataDTO.setPassportIssueBranch("Улица Пушкина - Дом Колотушкина");
        scoringDataDTO.setMaritalStatus(MaritalStatus.DIVORCED);
        scoringDataDTO.setDependentAmount(5);
        scoringDataDTO.setEmployment(employment2);
        scoringDataDTO.setAccount("40817810099910004312");
        scoringDataDTO.setIsInsuranceEnabled(true);
        scoringDataDTO.setIsSalaryClient(true);


        List<PaymentScheduleElement> paymentSchedule2 = new ArrayList<>();

        paymentScheduleElement1.setNumber(1);
        paymentScheduleElement1.setDate(LocalDate.of(2022, 7, 6));
        paymentScheduleElement1.setTotalPayment(BigDecimal.valueOf(107895.9000000).setScale(7));
        paymentScheduleElement1.setInterestPayment(BigDecimal.valueOf(2895.9000000).setScale(7));
        paymentScheduleElement1.setDebtPayment(BigDecimal.valueOf(107895.9000000).setScale(7));
        paymentScheduleElement1.setRemainingDebt(BigDecimal.valueOf(107895.9000000).setScale(7));

        paymentScheduleElement2.setNumber(2);
        paymentScheduleElement2.setDate(LocalDate.of(2022, 8, 6));
        paymentScheduleElement2.setTotalPayment(BigDecimal.valueOf(215791.8000000).setScale(7));
        paymentScheduleElement2.setInterestPayment(BigDecimal.valueOf(2895.9000000).setScale(7));
        paymentScheduleElement2.setDebtPayment(BigDecimal.valueOf(107895.9000000).setScale(7));
        paymentScheduleElement2.setRemainingDebt(BigDecimal.valueOf(0.00000).setScale(7));

        paymentSchedule2.add(paymentScheduleElement1);
        paymentSchedule2.add(paymentScheduleElement2);

        creditDTO.setAmount(BigDecimal.valueOf(210000.00).setScale(2));
        creditDTO.setTerm(2);
        creditDTO.setMonthlyPayment(BigDecimal.valueOf(107895.9000000).setScale(7));
        creditDTO.setRate(BigDecimal.valueOf(22));
        creditDTO.setPsk(BigDecimal.valueOf(215791.8000000).setScale(7));
        creditDTO.setIsInsuranceEnabled(true);
        creditDTO.setIsSalaryClient(true);
        creditDTO.setPaymentSchedule(paymentSchedule);


        Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
    }

    @Test
    void getPaymentScheduleElement() {
        ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
        ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        PaymentScheduleElement paymentScheduleElement1 = new PaymentScheduleElement();
        paymentScheduleElement1.setNumber(1);
        paymentScheduleElement1.setDate(LocalDate.of(2022, 7, 6));
        paymentScheduleElement1.setTotalPayment(BigDecimal.valueOf(101754.00000).setScale(5));
        paymentScheduleElement1.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
        paymentScheduleElement1.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
        paymentScheduleElement1.setRemainingDebt(BigDecimal.valueOf(101754.00000).setScale(5));

        PaymentScheduleElement paymentScheduleElement2 = new PaymentScheduleElement();
        paymentScheduleElement2.setNumber(2);
        paymentScheduleElement2.setDate(LocalDate.of(2022, 8, 6));
        paymentScheduleElement2.setTotalPayment(BigDecimal.valueOf(203508.00000).setScale(5));
        paymentScheduleElement2.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
        paymentScheduleElement2.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
        paymentScheduleElement2.setRemainingDebt(BigDecimal.valueOf(0.00000).setScale(5));

        paymentSchedule.add(paymentScheduleElement1);
        paymentSchedule.add(paymentScheduleElement2);


        Assertions.assertEquals(paymentSchedule, conveyorService.getPaymentScheduleElement(2, BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(200000), BigDecimal.valueOf(203508.00000).setScale(5)));

    }

    @Test
    void testExpectedExceptionWithUnemployed() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = new EmploymentDTO();
            employment1.setEmploymentStatus(EmploymentStatus.UNEMPLOYED);
            employment1.setEmployerINN("7727563778");
            employment1.setSalary(BigDecimal.valueOf(10000));
            employment1.setPosition(Position.MIDDLE_MANAGER);
            employment1.setWorkExperienceTotal(30);
            employment1.setWorkExperienceCurrent(5);

            ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
            scoringDataDTO.setAmount(BigDecimal.valueOf(200000));
            scoringDataDTO.setTerm(2);
            scoringDataDTO.setFirstName("Николай");
            scoringDataDTO.setLastName("Козьяков");
            scoringDataDTO.setMiddleName("Николаевич");
            scoringDataDTO.setGender(Genders.MALE);
            scoringDataDTO.setBirthdate(LocalDate.of(1980, 9, 29));
            scoringDataDTO.setPassportSeries("1234");
            scoringDataDTO.setPassportNumber("123456");
            scoringDataDTO.setPassportIssueDate(LocalDate.of(2002, 9, 29));
            scoringDataDTO.setPassportIssueBranch("Улица Пушкина - Дом Колотушкина");
            scoringDataDTO.setMaritalStatus(MaritalStatus.MARRIED_MARRIED);
            scoringDataDTO.setDependentAmount(1);
            scoringDataDTO.setEmployment(employment1);
            scoringDataDTO.setAccount("40817810099910004312");
            scoringDataDTO.setIsInsuranceEnabled(false);
            scoringDataDTO.setIsSalaryClient(true);


            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = new PaymentScheduleElement();
            paymentScheduleElement1.setNumber(1);
            paymentScheduleElement1.setDate(LocalDate.of(2022, 7, 6));
            paymentScheduleElement1.setTotalPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement1.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setRemainingDebt(BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = new PaymentScheduleElement();
            paymentScheduleElement2.setNumber(2);
            paymentScheduleElement2.setDate(LocalDate.of(2022, 8, 6));
            paymentScheduleElement2.setTotalPayment(BigDecimal.valueOf(203508.00000).setScale(5));
            paymentScheduleElement2.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement2.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement2.setRemainingDebt(BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = new CreditDTO();
            creditDTO.setAmount(BigDecimal.valueOf(200000));
            creditDTO.setTerm(2);
            creditDTO.setMonthlyPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            creditDTO.setRate(BigDecimal.valueOf(14));
            creditDTO.setPsk(BigDecimal.valueOf(203508.00000).setScale(5));
            creditDTO.setIsInsuranceEnabled(false);
            creditDTO.setIsSalaryClient(true);
            creditDTO.setPaymentSchedule(paymentSchedule);


            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithAmount() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = new EmploymentDTO();
            employment1.setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);
            employment1.setEmployerINN("7727563778");
            employment1.setSalary(BigDecimal.valueOf(10000));
            employment1.setPosition(Position.MIDDLE_MANAGER);
            employment1.setWorkExperienceTotal(30);
            employment1.setWorkExperienceCurrent(5);

            ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
            scoringDataDTO.setAmount(BigDecimal.valueOf(500000));
            scoringDataDTO.setTerm(2);
            scoringDataDTO.setFirstName("Николай");
            scoringDataDTO.setLastName("Козьяков");
            scoringDataDTO.setMiddleName("Николаевич");
            scoringDataDTO.setGender(Genders.MALE);
            scoringDataDTO.setBirthdate(LocalDate.of(1980, 9, 29));
            scoringDataDTO.setPassportSeries("1234");
            scoringDataDTO.setPassportNumber("123456");
            scoringDataDTO.setPassportIssueDate(LocalDate.of(2002, 9, 29));
            scoringDataDTO.setPassportIssueBranch("Улица Пушкина - Дом Колотушкина");
            scoringDataDTO.setMaritalStatus(MaritalStatus.MARRIED_MARRIED);
            scoringDataDTO.setDependentAmount(1);
            scoringDataDTO.setEmployment(employment1);
            scoringDataDTO.setAccount("40817810099910004312");
            scoringDataDTO.setIsInsuranceEnabled(false);
            scoringDataDTO.setIsSalaryClient(true);


            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = new PaymentScheduleElement();
            paymentScheduleElement1.setNumber(1);
            paymentScheduleElement1.setDate(LocalDate.of(2022, 7, 6));
            paymentScheduleElement1.setTotalPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement1.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setRemainingDebt(BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = new PaymentScheduleElement();
            paymentScheduleElement2.setNumber(2);
            paymentScheduleElement2.setDate(LocalDate.of(2022, 8, 6));
            paymentScheduleElement2.setTotalPayment(BigDecimal.valueOf(203508.00000).setScale(5));
            paymentScheduleElement2.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement2.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement2.setRemainingDebt(BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = new CreditDTO();
            creditDTO.setAmount(BigDecimal.valueOf(200000));
            creditDTO.setTerm(2);
            creditDTO.setMonthlyPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            creditDTO.setRate(BigDecimal.valueOf(14));
            creditDTO.setPsk(BigDecimal.valueOf(203508.00000).setScale(5));
            creditDTO.setIsInsuranceEnabled(false);
            creditDTO.setIsSalaryClient(true);
            creditDTO.setPaymentSchedule(paymentSchedule);


            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithBirthdate20() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = new EmploymentDTO();
            employment1.setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);
            employment1.setEmployerINN("7727563778");
            employment1.setSalary(BigDecimal.valueOf(10000));
            employment1.setPosition(Position.MIDDLE_MANAGER);
            employment1.setWorkExperienceTotal(30);
            employment1.setWorkExperienceCurrent(5);

            ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
            scoringDataDTO.setAmount(BigDecimal.valueOf(150000));
            scoringDataDTO.setTerm(2);
            scoringDataDTO.setFirstName("Николай");
            scoringDataDTO.setLastName("Козьяков");
            scoringDataDTO.setMiddleName("Николаевич");
            scoringDataDTO.setGender(Genders.MALE);
            scoringDataDTO.setBirthdate(LocalDate.of(2020, 9, 29));
            scoringDataDTO.setPassportSeries("1234");
            scoringDataDTO.setPassportNumber("123456");
            scoringDataDTO.setPassportIssueDate(LocalDate.of(2002, 9, 29));
            scoringDataDTO.setPassportIssueBranch("Улица Пушкина - Дом Колотушкина");
            scoringDataDTO.setMaritalStatus(MaritalStatus.MARRIED_MARRIED);
            scoringDataDTO.setDependentAmount(1);
            scoringDataDTO.setEmployment(employment1);
            scoringDataDTO.setAccount("40817810099910004312");
            scoringDataDTO.setIsInsuranceEnabled(false);
            scoringDataDTO.setIsSalaryClient(true);


            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = new PaymentScheduleElement();
            paymentScheduleElement1.setNumber(1);
            paymentScheduleElement1.setDate(LocalDate.of(2022, 7, 6));
            paymentScheduleElement1.setTotalPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement1.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setRemainingDebt(BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = new PaymentScheduleElement();
            paymentScheduleElement2.setNumber(2);
            paymentScheduleElement2.setDate(LocalDate.of(2022, 8, 6));
            paymentScheduleElement2.setTotalPayment(BigDecimal.valueOf(203508.00000).setScale(5));
            paymentScheduleElement2.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement2.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement2.setRemainingDebt(BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = new CreditDTO();
            creditDTO.setAmount(BigDecimal.valueOf(200000));
            creditDTO.setTerm(2);
            creditDTO.setMonthlyPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            creditDTO.setRate(BigDecimal.valueOf(14));
            creditDTO.setPsk(BigDecimal.valueOf(203508.00000).setScale(5));
            creditDTO.setIsInsuranceEnabled(false);
            creditDTO.setIsSalaryClient(true);
            creditDTO.setPaymentSchedule(paymentSchedule);


            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithBirthdate60() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = new EmploymentDTO();
            employment1.setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);
            employment1.setEmployerINN("7727563778");
            employment1.setSalary(BigDecimal.valueOf(10000));
            employment1.setPosition(Position.MIDDLE_MANAGER);
            employment1.setWorkExperienceTotal(30);
            employment1.setWorkExperienceCurrent(5);

            ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
            scoringDataDTO.setAmount(BigDecimal.valueOf(150000));
            scoringDataDTO.setTerm(2);
            scoringDataDTO.setFirstName("Николай");
            scoringDataDTO.setLastName("Козьяков");
            scoringDataDTO.setMiddleName("Николаевич");
            scoringDataDTO.setGender(Genders.MALE);
            scoringDataDTO.setBirthdate(LocalDate.of(1900, 9, 29));
            scoringDataDTO.setPassportSeries("1234");
            scoringDataDTO.setPassportNumber("123456");
            scoringDataDTO.setPassportIssueDate(LocalDate.of(2002, 9, 29));
            scoringDataDTO.setPassportIssueBranch("Улица Пушкина - Дом Колотушкина");
            scoringDataDTO.setMaritalStatus(MaritalStatus.MARRIED_MARRIED);
            scoringDataDTO.setDependentAmount(1);
            scoringDataDTO.setEmployment(employment1);
            scoringDataDTO.setAccount("40817810099910004312");
            scoringDataDTO.setIsInsuranceEnabled(false);
            scoringDataDTO.setIsSalaryClient(true);


            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = new PaymentScheduleElement();
            paymentScheduleElement1.setNumber(1);
            paymentScheduleElement1.setDate(LocalDate.of(2022, 7, 6));
            paymentScheduleElement1.setTotalPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement1.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setRemainingDebt(BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = new PaymentScheduleElement();
            paymentScheduleElement2.setNumber(2);
            paymentScheduleElement2.setDate(LocalDate.of(2022, 8, 6));
            paymentScheduleElement2.setTotalPayment(BigDecimal.valueOf(203508.00000).setScale(5));
            paymentScheduleElement2.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement2.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement2.setRemainingDebt(BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = new CreditDTO();
            creditDTO.setAmount(BigDecimal.valueOf(200000));
            creditDTO.setTerm(2);
            creditDTO.setMonthlyPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            creditDTO.setRate(BigDecimal.valueOf(14));
            creditDTO.setPsk(BigDecimal.valueOf(203508.00000).setScale(5));
            creditDTO.setIsInsuranceEnabled(false);
            creditDTO.setIsSalaryClient(true);
            creditDTO.setPaymentSchedule(paymentSchedule);


            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithWorkExperienceTotal() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = new EmploymentDTO();
            employment1.setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);
            employment1.setEmployerINN("7727563778");
            employment1.setSalary(BigDecimal.valueOf(10000));
            employment1.setPosition(Position.MIDDLE_MANAGER);
            employment1.setWorkExperienceTotal(1);
            employment1.setWorkExperienceCurrent(1);

            ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
            scoringDataDTO.setAmount(BigDecimal.valueOf(150000));
            scoringDataDTO.setTerm(2);
            scoringDataDTO.setFirstName("Николай");
            scoringDataDTO.setLastName("Козьяков");
            scoringDataDTO.setMiddleName("Николаевич");
            scoringDataDTO.setGender(Genders.NOT_BINARY);
            scoringDataDTO.setBirthdate(LocalDate.of(1980, 9, 29));
            scoringDataDTO.setPassportSeries("1234");
            scoringDataDTO.setPassportNumber("123456");
            scoringDataDTO.setPassportIssueDate(LocalDate.of(2002, 9, 29));
            scoringDataDTO.setPassportIssueBranch("Улица Пушкина - Дом Колотушкина");
            scoringDataDTO.setMaritalStatus(MaritalStatus.MARRIED_MARRIED);
            scoringDataDTO.setDependentAmount(1);
            scoringDataDTO.setEmployment(employment1);
            scoringDataDTO.setAccount("40817810099910004312");
            scoringDataDTO.setIsInsuranceEnabled(false);
            scoringDataDTO.setIsSalaryClient(true);


            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = new PaymentScheduleElement();
            paymentScheduleElement1.setNumber(1);
            paymentScheduleElement1.setDate(LocalDate.of(2022, 7, 6));
            paymentScheduleElement1.setTotalPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement1.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setRemainingDebt(BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = new PaymentScheduleElement();
            paymentScheduleElement2.setNumber(2);
            paymentScheduleElement2.setDate(LocalDate.of(2022, 8, 6));
            paymentScheduleElement2.setTotalPayment(BigDecimal.valueOf(203508.00000).setScale(5));
            paymentScheduleElement2.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement2.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement2.setRemainingDebt(BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = new CreditDTO();
            creditDTO.setAmount(BigDecimal.valueOf(200000));
            creditDTO.setTerm(2);
            creditDTO.setMonthlyPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            creditDTO.setRate(BigDecimal.valueOf(14));
            creditDTO.setPsk(BigDecimal.valueOf(203508.00000).setScale(5));
            creditDTO.setIsInsuranceEnabled(false);
            creditDTO.setIsSalaryClient(true);
            creditDTO.setPaymentSchedule(paymentSchedule);


            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithWorkExperienceCurrent() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = new EmploymentDTO();
            employment1.setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);
            employment1.setEmployerINN("7727563778");
            employment1.setSalary(BigDecimal.valueOf(10000));
            employment1.setPosition(Position.MIDDLE_MANAGER);
            employment1.setWorkExperienceTotal(30);
            employment1.setWorkExperienceCurrent(1);

            ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
            scoringDataDTO.setAmount(BigDecimal.valueOf(150000));
            scoringDataDTO.setTerm(2);
            scoringDataDTO.setFirstName("Николай");
            scoringDataDTO.setLastName("Козьяков");
            scoringDataDTO.setMiddleName("Николаевич");
            scoringDataDTO.setGender(Genders.NOT_BINARY);
            scoringDataDTO.setBirthdate(LocalDate.of(1980, 9, 29));
            scoringDataDTO.setPassportSeries("1234");
            scoringDataDTO.setPassportNumber("123456");
            scoringDataDTO.setPassportIssueDate(LocalDate.of(2002, 9, 29));
            scoringDataDTO.setPassportIssueBranch("Улица Пушкина - Дом Колотушкина");
            scoringDataDTO.setMaritalStatus(MaritalStatus.MARRIED_MARRIED);
            scoringDataDTO.setDependentAmount(1);
            scoringDataDTO.setEmployment(employment1);
            scoringDataDTO.setAccount("40817810099910004312");
            scoringDataDTO.setIsInsuranceEnabled(false);
            scoringDataDTO.setIsSalaryClient(true);


            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = new PaymentScheduleElement();
            paymentScheduleElement1.setNumber(1);
            paymentScheduleElement1.setDate(LocalDate.of(2022, 7, 6));
            paymentScheduleElement1.setTotalPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement1.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement1.setRemainingDebt(BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = new PaymentScheduleElement();
            paymentScheduleElement2.setNumber(2);
            paymentScheduleElement2.setDate(LocalDate.of(2022, 8, 6));
            paymentScheduleElement2.setTotalPayment(BigDecimal.valueOf(203508.00000).setScale(5));
            paymentScheduleElement2.setInterestPayment(BigDecimal.valueOf(1754.00000).setScale(5));
            paymentScheduleElement2.setDebtPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            paymentScheduleElement2.setRemainingDebt(BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = new CreditDTO();
            creditDTO.setAmount(BigDecimal.valueOf(200000));
            creditDTO.setTerm(2);
            creditDTO.setMonthlyPayment(BigDecimal.valueOf(101754.00000).setScale(5));
            creditDTO.setRate(BigDecimal.valueOf(14));
            creditDTO.setPsk(BigDecimal.valueOf(203508.00000).setScale(5));
            creditDTO.setIsInsuranceEnabled(false);
            creditDTO.setIsSalaryClient(true);
            creditDTO.setPaymentSchedule(paymentSchedule);


            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }
}