package com.neoflex.conveyor.services;

import com.neoflex.conveyor.Calculations;
import com.neoflex.conveyor.dto.*;
import com.neoflex.conveyor.exceptions.ScoringException;
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

        LoanApplicationRequestDTO loanApplicationRequestDTO = getLoanApplicationRequestDTO(BigDecimal.valueOf(10000), 6, "Николай", "Козьяков", "Николаевич", "uservice371@mail.ru", LocalDate.of(1991, 9, 26), "1234", "123456");

        List<LoanOfferDTO> loanOfferList = new ArrayList<>();

        LoanOfferDTO loanOfferDTO1 = getLoanOfferDTO(null, BigDecimal.valueOf(10000), BigDecimal.valueOf(10995.3900000).setScale(7), 6, BigDecimal.valueOf(1832.5650000).setScale(7), BigDecimal.valueOf(16), true, true);

        loanOfferList.add(loanOfferDTO1);

        LoanOfferDTO loanOfferDTO2 = getLoanOfferDTO(null, BigDecimal.valueOf(10000), BigDecimal.valueOf(11026.8900000).setScale(7), 6, BigDecimal.valueOf(1837.8150000).setScale(7), BigDecimal.valueOf(17), true, false);

        loanOfferList.add(loanOfferDTO2);

        LoanOfferDTO loanOfferDTO3 = getLoanOfferDTO(null, BigDecimal.valueOf(10000), BigDecimal.valueOf(10591.20000).setScale(5), 6, BigDecimal.valueOf(1765.20000).setScale(5), BigDecimal.valueOf(20), false, false);

        loanOfferList.add(loanOfferDTO3);

        LoanOfferDTO loanOfferDTO4 = getLoanOfferDTO(null, BigDecimal.valueOf(10000), BigDecimal.valueOf(10561.20000).setScale(5), 6, BigDecimal.valueOf(1760.20000).setScale(5), BigDecimal.valueOf(19), false, true);

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

        LoanApplicationRequestDTO loanApplicationRequestDTO = getLoanApplicationRequestDTO(BigDecimal.valueOf(10000), 6, "Николай", "Козьяков", "Николаевич", "uservice371@mail.ru",
                LocalDate.of(1991, 9, 26), "1234", "123456");

        LoanOfferDTO loanOfferDTO1 = getLoanOfferDTO(null, BigDecimal.valueOf(10000), BigDecimal.valueOf(10995.3900000).setScale(7), 6, BigDecimal.valueOf(1832.5650000).setScale(7), BigDecimal.valueOf(16), true, true);

        Assertions.assertEquals(loanOfferDTO1, conveyorService.formationOfOffers(loanApplicationRequestDTO, true, true));

        LoanOfferDTO loanOfferDTO2 = getLoanOfferDTO(null, BigDecimal.valueOf(10000), BigDecimal.valueOf(11026.8900000).setScale(7), 6, BigDecimal.valueOf(1837.8150000).setScale(7), BigDecimal.valueOf(17), true, false);

        Assertions.assertEquals(loanOfferDTO2, conveyorService.formationOfOffers(loanApplicationRequestDTO, true, false));

        LoanOfferDTO loanOfferDTO3 = getLoanOfferDTO(null, BigDecimal.valueOf(10000), BigDecimal.valueOf(10591.20000).setScale(5), 6, BigDecimal.valueOf(1765.20000).setScale(5), BigDecimal.valueOf(20), false, false);

        Assertions.assertEquals(loanOfferDTO3, conveyorService.formationOfOffers(loanApplicationRequestDTO, false, false));

        LoanOfferDTO loanOfferDTO4 = getLoanOfferDTO(null, BigDecimal.valueOf(10000), BigDecimal.valueOf(10561.20000).setScale(5), 6, BigDecimal.valueOf(1760.20000).setScale(5), BigDecimal.valueOf(19), false, true);

        Assertions.assertEquals(loanOfferDTO4, conveyorService.formationOfOffers(loanApplicationRequestDTO, false, true));

    }

    @Test
    void loanCalculation() {
        ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
        ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

        EmploymentDTO employment1 = getEmploymentDTO(EmploymentStatus.BUSINESS_OWNER, "7727563778", BigDecimal.valueOf(10000), Position.MIDDLE_MANAGER, 30, 5);

        ScoringDataDTO scoringDataDTO = getScoringDataDTO(BigDecimal.valueOf(200000), 2, "Николай", "Козьяков", "Николаевич", Genders.MALE, LocalDate.of(1980, 9, 29),
                "1234", "123456", LocalDate.of(2002, 9, 29), "Улица Пушкина - Дом Колотушкина", MaritalStatus.MARRIED_MARRIED, 1, employment1, "40817810099910004312", false, true);

        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        PaymentScheduleElement paymentScheduleElement1 = getPaymentScheduleElement(1, LocalDate.now().plusMonths(1), BigDecimal.valueOf(101754.00000).setScale(5),
                BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5));

        PaymentScheduleElement paymentScheduleElement2 = getPaymentScheduleElement(2, LocalDate.now().plusMonths(2), BigDecimal.valueOf(203508.00000).setScale(5),
                BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(0.00000).setScale(5));

        paymentSchedule.add(paymentScheduleElement1);
        paymentSchedule.add(paymentScheduleElement2);

        CreditDTO creditDTO = getCreditDTO(BigDecimal.valueOf(200000), 2, BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(14), BigDecimal.valueOf(203508.00000).setScale(5),
                false, true, paymentSchedule);

        Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));

        EmploymentDTO employment2 = getEmploymentDTO(EmploymentStatus.SELF_EMPLOYED, "7727563778", BigDecimal.valueOf(10000), Position.TOP_MANAGER, 30, 5);

        scoringDataDTO = getScoringDataDTO(BigDecimal.valueOf(200000), 2, "Зинаида", "Занаииииииида", "Зинаидовна", Genders.WOMAN, LocalDate.of(1980, 9, 29),
                "1234", "123456", LocalDate.of(2002, 9, 29), "Улица Пушкина - Дом Колотушкина", MaritalStatus.DIVORCED, 5, employment2, "40817810099910004312", true, true);

        List<PaymentScheduleElement> paymentSchedule2 = new ArrayList<>();

        paymentScheduleElement1 = getPaymentScheduleElement(1, LocalDate.now().plusMonths(1), BigDecimal.valueOf(107895.9000000).setScale(7), BigDecimal.valueOf(2895.9000000).setScale(7),
                BigDecimal.valueOf(107895.9000000).setScale(7), BigDecimal.valueOf(107895.9000000).setScale(7));

        paymentScheduleElement2 = getPaymentScheduleElement(2, LocalDate.now().plusMonths(2), BigDecimal.valueOf(215791.8000000).setScale(7), BigDecimal.valueOf(2895.9000000).setScale(7),
                BigDecimal.valueOf(107895.9000000).setScale(7), BigDecimal.valueOf(0.00000).setScale(7));

        paymentSchedule2.add(paymentScheduleElement1);
        paymentSchedule2.add(paymentScheduleElement2);

        creditDTO = getCreditDTO(BigDecimal.valueOf(210000.00).setScale(2), 2, BigDecimal.valueOf(107895.9000000).setScale(7), BigDecimal.valueOf(22), BigDecimal.valueOf(215791.8000000).setScale(7),
                true, true, paymentSchedule2);

        Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
    }

    @Test
    void getPaymentScheduleElement() {
        ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
        ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        PaymentScheduleElement paymentScheduleElement1 = getPaymentScheduleElement(1, LocalDate.now().plusMonths(1), BigDecimal.valueOf(101754.00000).setScale(5),
                BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5));

        PaymentScheduleElement paymentScheduleElement2 = getPaymentScheduleElement(2, LocalDate.now().plusMonths(2), BigDecimal.valueOf(203508.00000).setScale(5),
                BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(0.00000).setScale(5));

        paymentSchedule.add(paymentScheduleElement1);
        paymentSchedule.add(paymentScheduleElement2);

        Assertions.assertEquals(paymentSchedule, conveyorService.getPaymentScheduleElement(2, BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(200000), BigDecimal.valueOf(203508.00000).setScale(5)));

    }

    @Test
    void testExpectedExceptionWithUnemployed() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = getEmploymentDTO(EmploymentStatus.UNEMPLOYED, "7727563778", BigDecimal.valueOf(10000), Position.MIDDLE_MANAGER, 30, 5);

            ScoringDataDTO scoringDataDTO = getScoringDataDTO(BigDecimal.valueOf(200000), 2, "Николай", "Козьяков", "Николаевич", Genders.MALE, LocalDate.of(1980, 9, 29),
                    "1234", "123456", LocalDate.of(2002, 9, 29), "Улица Пушкина - Дом Колотушкина", MaritalStatus.MARRIED_MARRIED, 1, employment1, "40817810099910004312", false, true);

            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = getPaymentScheduleElement(1, LocalDate.now().plusMonths(1), BigDecimal.valueOf(101754.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = getPaymentScheduleElement(2, LocalDate.now().plusMonths(2), BigDecimal.valueOf(203508.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = getCreditDTO(BigDecimal.valueOf(200000), 2, BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(14), BigDecimal.valueOf(203508.00000).setScale(5),
                    false, true, paymentSchedule);

            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithAmount() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = getEmploymentDTO(EmploymentStatus.SELF_EMPLOYED, "7727563778", BigDecimal.valueOf(10000), Position.MIDDLE_MANAGER, 30, 5);

            ScoringDataDTO scoringDataDTO = getScoringDataDTO(BigDecimal.valueOf(500000), 2, "Николай", "Козьяков", "Николаевич", Genders.MALE, LocalDate.of(1980, 9, 29),
                    "1234", "123456", LocalDate.of(2002, 9, 29), "Улица Пушкина - Дом Колотушкина", MaritalStatus.MARRIED_MARRIED, 1, employment1, "40817810099910004312", false, true);

            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = getPaymentScheduleElement(1, LocalDate.now().plusMonths(1), BigDecimal.valueOf(101754.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = getPaymentScheduleElement(2, LocalDate.now().plusMonths(2), BigDecimal.valueOf(203508.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = getCreditDTO(BigDecimal.valueOf(200000), 2, BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(14), BigDecimal.valueOf(203508.00000).setScale(5),
                    false, true, paymentSchedule);

            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithBirthdate20() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = getEmploymentDTO(EmploymentStatus.SELF_EMPLOYED, "7727563778", BigDecimal.valueOf(10000), Position.MIDDLE_MANAGER, 30, 5);

            ScoringDataDTO scoringDataDTO = getScoringDataDTO(BigDecimal.valueOf(150000), 2, "Николай", "Козьяков", "Николаевич", Genders.MALE, LocalDate.of(2020, 9, 29),
                    "1234", "123456", LocalDate.of(2002, 9, 29), "Улица Пушкина - Дом Колотушкина", MaritalStatus.MARRIED_MARRIED, 1, employment1, "40817810099910004312", false, true);

            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = getPaymentScheduleElement(1, LocalDate.now().plusMonths(1), BigDecimal.valueOf(101754.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = getPaymentScheduleElement(2, LocalDate.now().plusMonths(2), BigDecimal.valueOf(203508.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = getCreditDTO(BigDecimal.valueOf(200000), 2, BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(14), BigDecimal.valueOf(203508.00000).setScale(5),
                    false, true, paymentSchedule);

            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithBirthdate60() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = getEmploymentDTO(EmploymentStatus.SELF_EMPLOYED, "7727563778", BigDecimal.valueOf(10000), Position.MIDDLE_MANAGER, 30, 5);

            ScoringDataDTO scoringDataDTO = getScoringDataDTO(BigDecimal.valueOf(150000), 2, "Николай", "Козьяков", "Николаевич", Genders.MALE, LocalDate.of(1900, 9, 29),
                    "1234", "123456", LocalDate.of(2002, 9, 29), "Улица Пушкина - Дом Колотушкина", MaritalStatus.MARRIED_MARRIED, 1, employment1, "40817810099910004312", false, true);

            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = getPaymentScheduleElement(1, LocalDate.now().plusMonths(1), BigDecimal.valueOf(101754.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = getPaymentScheduleElement(2, LocalDate.now().plusMonths(2), BigDecimal.valueOf(203508.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = getCreditDTO(BigDecimal.valueOf(200000), 2, BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(14),
                    BigDecimal.valueOf(203508.00000).setScale(5), false, true, paymentSchedule);

            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithWorkExperienceTotal() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = getEmploymentDTO(EmploymentStatus.SELF_EMPLOYED, "7727563778", BigDecimal.valueOf(10000), Position.MIDDLE_MANAGER, 1, 1);

            ScoringDataDTO scoringDataDTO = getScoringDataDTO(BigDecimal.valueOf(150000), 2, "Николай", "Козьяков", "Николаевич", Genders.NOT_BINARY, LocalDate.of(1980, 9, 29),
                    "1234", "123456", LocalDate.of(2002, 9, 29), "Улица Пушкина - Дом Колотушкина", MaritalStatus.MARRIED_MARRIED, 1, employment1, "40817810099910004312", false, true);

            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = getPaymentScheduleElement(1, LocalDate.now().plusMonths(1), BigDecimal.valueOf(101754.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = getPaymentScheduleElement(2, LocalDate.now().plusMonths(2), BigDecimal.valueOf(203508.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = getCreditDTO((BigDecimal.valueOf(200000)), 2, BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(14),
                    BigDecimal.valueOf(203508.00000).setScale(5), false, true, paymentSchedule);

            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    @Test
    void testExpectedExceptionWithWorkExperienceCurrent() {
        Assertions.assertThrows(ScoringException.class, () -> {
            ReflectionTestUtils.setField(conveyorService, "BaseRate", BigDecimal.valueOf(20));
            ReflectionTestUtils.setField(conveyorService, "calculations", calculations);

            EmploymentDTO employment1 = getEmploymentDTO(EmploymentStatus.SELF_EMPLOYED, "7727563778", BigDecimal.valueOf(10000), Position.MIDDLE_MANAGER, 30, 1);

            ScoringDataDTO scoringDataDTO = getScoringDataDTO(BigDecimal.valueOf(150000), 2, "Николай", "Козьяков", "Николаевич", Genders.NOT_BINARY, LocalDate.of(1980, 9, 29), "1234",
                    "123456", LocalDate.of(2002, 9, 29), "Улица Пушкина - Дом Колотушкина", MaritalStatus.MARRIED_MARRIED, 1, employment1, "40817810099910004312", false, true);

            List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

            PaymentScheduleElement paymentScheduleElement1 = getPaymentScheduleElement(1, LocalDate.now().plusMonths(1), BigDecimal.valueOf(101754.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5));

            PaymentScheduleElement paymentScheduleElement2 = getPaymentScheduleElement(2, LocalDate.now().plusMonths(2), BigDecimal.valueOf(203508.00000).setScale(5),
                    BigDecimal.valueOf(1754.00000).setScale(5), BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(0.00000).setScale(5));

            paymentSchedule.add(paymentScheduleElement1);
            paymentSchedule.add(paymentScheduleElement2);

            CreditDTO creditDTO = getCreditDTO(BigDecimal.valueOf(200000), 2, BigDecimal.valueOf(101754.00000).setScale(5), BigDecimal.valueOf(14),
                    BigDecimal.valueOf(203508.00000).setScale(5), false, true, paymentSchedule);

            Assertions.assertEquals(creditDTO, conveyorService.loanCalculation(scoringDataDTO));
        });
    }

    private CreditDTO getCreditDTO(BigDecimal amount, Integer term, BigDecimal monthlyPayment, BigDecimal rate, BigDecimal psk, Boolean isInsuranceEnabled, Boolean isSalaryClient, List<PaymentScheduleElement> paymentSchedule) {

        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setAmount(amount);
        creditDTO.setTerm(term);
        creditDTO.setMonthlyPayment(monthlyPayment);
        creditDTO.setRate(rate);
        creditDTO.setPsk(psk);
        creditDTO.setIsInsuranceEnabled(isInsuranceEnabled);
        creditDTO.setIsSalaryClient(isSalaryClient);
        creditDTO.setPaymentSchedule(paymentSchedule);

        return creditDTO;
    }

    private PaymentScheduleElement getPaymentScheduleElement(Integer number, LocalDate date, BigDecimal totalPayment, BigDecimal interestPayment, BigDecimal debtPayment, BigDecimal remainingDebt) {
        PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
        paymentScheduleElement.setNumber(number);
        paymentScheduleElement.setDate(date);
        paymentScheduleElement.setTotalPayment(totalPayment);
        paymentScheduleElement.setInterestPayment(interestPayment);
        paymentScheduleElement.setDebtPayment(debtPayment);
        paymentScheduleElement.setRemainingDebt(remainingDebt);
        return paymentScheduleElement;
    }

    private EmploymentDTO getEmploymentDTO(EmploymentStatus employmentStatus, String INN, BigDecimal salary, Position position, Integer workExperienceTotal, Integer workExperienceCurrent) {

        EmploymentDTO employment = new EmploymentDTO();
        employment.setEmploymentStatus(employmentStatus);
        employment.setEmployerINN(INN);
        employment.setSalary(salary);
        employment.setPosition(position);
        employment.setWorkExperienceTotal(workExperienceTotal);
        employment.setWorkExperienceCurrent(workExperienceCurrent);
        return employment;
    }

    private ScoringDataDTO getScoringDataDTO(BigDecimal amount, Integer term, String firstName, String lastName, String middleName, Genders genders, LocalDate birthdate, String passportSeries, String passportNumber,
                                             LocalDate passportIssueDate, String passportIssueBranch, MaritalStatus status, Integer dependentAmount, EmploymentDTO employment, String account, Boolean isInsuranceEnabled, Boolean isSalaryClient) {

        ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
        scoringDataDTO.setAmount(amount);
        scoringDataDTO.setTerm(term);
        scoringDataDTO.setFirstName(firstName);
        scoringDataDTO.setLastName(lastName);
        scoringDataDTO.setMiddleName(middleName);
        scoringDataDTO.setGender(genders);
        scoringDataDTO.setBirthdate(birthdate);
        scoringDataDTO.setPassportSeries(passportSeries);
        scoringDataDTO.setPassportNumber(passportNumber);
        scoringDataDTO.setPassportIssueDate(passportIssueDate);
        scoringDataDTO.setPassportIssueBranch(passportIssueBranch);
        scoringDataDTO.setMaritalStatus(status);
        scoringDataDTO.setDependentAmount(dependentAmount);
        scoringDataDTO.setEmployment(employment);
        scoringDataDTO.setAccount(account);
        scoringDataDTO.setIsInsuranceEnabled(isInsuranceEnabled);
        scoringDataDTO.setIsSalaryClient(isSalaryClient);

        return scoringDataDTO;
    }

    private LoanOfferDTO getLoanOfferDTO(Long applicationId, BigDecimal requestedAmount, BigDecimal totalAmount, Integer term, BigDecimal monthlyPayment, BigDecimal rate, Boolean isInsuranceEnabled, Boolean isSalaryClient) {

        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        loanOfferDTO.setApplicationId(applicationId);
        loanOfferDTO.setRequestedAmount(requestedAmount);
        loanOfferDTO.setTotalAmount(totalAmount);
        loanOfferDTO.setTerm(term);
        loanOfferDTO.setMonthlyPayment(monthlyPayment);
        loanOfferDTO.setRate(rate);
        loanOfferDTO.setIsInsuranceEnabled(isInsuranceEnabled);
        loanOfferDTO.setIsSalaryClient(isSalaryClient);

        return loanOfferDTO;

    }

    private LoanApplicationRequestDTO getLoanApplicationRequestDTO(BigDecimal amount, Integer term, String firstName, String lastName, String middleName, String email, LocalDate birthdate, String passportSeries, String passportNumber) {

        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO();

        loanApplicationRequestDTO.setAmount(amount);
        loanApplicationRequestDTO.setTerm(term);
        loanApplicationRequestDTO.setFirstName(firstName);
        loanApplicationRequestDTO.setLastName(lastName);
        loanApplicationRequestDTO.setMiddleName(middleName);
        loanApplicationRequestDTO.setEmail(email);
        loanApplicationRequestDTO.setBirthdate(birthdate);
        loanApplicationRequestDTO.setPassportSeries(passportSeries);
        loanApplicationRequestDTO.setPassportNumber(passportNumber);

        return loanApplicationRequestDTO;
    }

}