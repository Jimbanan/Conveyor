package com.neoflex.conveyor.controllers;

import com.neoflex.conveyor.dto.CreditDTO;
import com.neoflex.conveyor.dto.LoanApplicationRequestDTO;
import com.neoflex.conveyor.dto.LoanOfferDTO;
import com.neoflex.conveyor.dto.ScoringDataDTO;
import com.neoflex.conveyor.services.ConveyorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RequestMapping(("/conveyor"))
@RestController
@Tag(name = "ConveyorController", description = "Кредитный конвейер")
public class ConveyorController {

    @Autowired
    ConveyorServiceImpl conveyorService;

    @PostMapping("/offers")
    @Operation(description = "Формирование списка кредитных предложение")
    public List<LoanOfferDTO> offers(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> loanOfferList = conveyorService.getOffers(loanApplicationRequestDTO);
        loanOfferList.sort(Comparator.comparing(LoanOfferDTO::getRate).reversed());
        return loanOfferList;
    }

    @PostMapping("/calculation")
    @Operation(description = "Расчет данных по кредитному предложению")
    public CreditDTO calculation(@RequestBody ScoringDataDTO scoringDataDTO) {
        return conveyorService.loanCalculation(scoringDataDTO);
    }

}
