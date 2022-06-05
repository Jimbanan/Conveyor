package com.neoflex.conveyor.controllers;

import com.neoflex.conveyor.DTO.CreditDTO;
import com.neoflex.conveyor.DTO.LoanApplicationRequestDTO;
import com.neoflex.conveyor.DTO.LoanOfferDTO;
import com.neoflex.conveyor.DTO.ScoringDataDTO;
import com.neoflex.conveyor.services.ConveyorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RequestMapping(("/conveyor"))
@RestController
public class ConveyorController {

    @Autowired
    ConveyorServiceImpl conveyorService;

    @PostMapping("/offers")
    public List<LoanOfferDTO> offers(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> loanOfferList = conveyorService.getOffers(loanApplicationRequestDTO);

        loanOfferList.sort(Comparator.comparing(LoanOfferDTO::getRate).reversed());

        return loanOfferList;
    }

    @PostMapping("/calculation")
    public CreditDTO calculation(@RequestBody ScoringDataDTO scoringDataDTO) {

        return conveyorService.loanCalculation(scoringDataDTO);
    }

}
