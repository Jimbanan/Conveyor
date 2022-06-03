package com.neoflex.conveyor.controllers;

import com.neoflex.conveyor.DTO.CreditDTO;
import com.neoflex.conveyor.DTO.LoanApplicationRequestDTO;
import com.neoflex.conveyor.DTO.LoanOfferDTO;
import com.neoflex.conveyor.DTO.ScoringDataDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(("/conveyor"))
@RestController
public class ConveyorController {

    @PostMapping("/offers")
    public List<LoanOfferDTO> offers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        //расчёт возможных условий кредита

        return null;
    }

    @PostMapping("/calculation")
    public CreditDTO calculation(ScoringDataDTO scoringDataDTO) {
        //валидация присланных данных + скоринг данных + полный расчет параметров кредита.

        return null;
    }

}
