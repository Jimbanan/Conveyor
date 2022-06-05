package com.neoflex.conveyor.controllers;

import com.neoflex.conveyor.DTO.*;
import com.neoflex.conveyor.enums.EmploymentStatus;
import com.neoflex.conveyor.enums.Genders;
import com.neoflex.conveyor.enums.MaritalStatus;
import com.neoflex.conveyor.enums.Position;
import com.neoflex.conveyor.services.ConveyorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@RequestMapping(("/conveyor"))
@RestController
public class ConveyorController {

    @Autowired
    ConveyorServiceImpl conveyorService;

    @PostMapping("/offers")
    public List<LoanOfferDTO> offers(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        //расчёт возможных условий кредита
        List<LoanOfferDTO> loanOfferList = conveyorService.getOffers(loanApplicationRequestDTO);

        loanOfferList.sort(Comparator.comparing(LoanOfferDTO::getRate).reversed());

//        loanOfferList

        return loanOfferList;
    }

    @PostMapping("/calculation")
    public CreditDTO calculation(@RequestBody ScoringDataDTO scoringDataDTO) {

        CreditDTO creditDTO = conveyorService.loanCalculation(scoringDataDTO);

        return creditDTO;
    }

//    @PostMapping("/calculation/test")
//    public ScoringDataDTO calculation_test() {
//
//
//        EmploymentDTO employmentDTO = new EmploymentDTO();
//        employmentDTO.setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
//        employmentDTO.setEmployerINN("7727563778");
//        employmentDTO.setSalary(BigDecimal.valueOf(100000));
//        employmentDTO.setPosition(Position.MIDDLE_MANAGER);
//        employmentDTO.setWorkExperienceTotal(10);
//        employmentDTO.setWorkExperienceCurrent(5);
//
//        System.out.println(employmentDTO);
//
//        return ScoringDataDTO.builder()
//                .account(null)
//                .term(6)
//                .firstName("Николай")
//                .lastName("Козьяков")
//                .middleName("Николаевич")
//                .gender(Genders.MALE)
//                .birthdate(null)
//                .passportSeries("1234")
//                .passportNumber("123456")
//                .passportIssueDate(null)
//                .passportIssueBranch("Улица Пушкина - Дом Колотушкина")
//                .maritalStatus(MaritalStatus.MARRIED_MARRIED)
//                .dependentAmount(1)
//                .employment(employmentDTO)
//                .account("40817810099910004312")
//                .isInsuranceEnabled(false)
//                .isSalaryClient(true)
//                .build();
//    }

}
