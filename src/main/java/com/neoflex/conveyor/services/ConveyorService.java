package com.neoflex.conveyor.services;

import com.neoflex.conveyor.DTO.CreditDTO;
import com.neoflex.conveyor.DTO.LoanApplicationRequestDTO;
import com.neoflex.conveyor.DTO.LoanOfferDTO;
import com.neoflex.conveyor.DTO.ScoringDataDTO;

import java.util.List;

public interface ConveyorService {

    List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO);

    LoanOfferDTO formationOfOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Boolean isInsuranceEnabled, Boolean isSalaryClient);

    CreditDTO loanCalculation(ScoringDataDTO scoringDataDTO);

}
