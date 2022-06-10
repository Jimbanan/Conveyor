package com.neoflex.conveyor.services;

import com.neoflex.conveyor.dto.FinishRegistrationRequestDTO;
import com.neoflex.conveyor.dto.LoanApplicationRequestDTO;
import com.neoflex.conveyor.dto.LoanOfferDTO;
import com.neoflex.conveyor.dto.ScoringDataDTO;

public interface DealService {

    Long addClient(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void addOffer(LoanOfferDTO loanOfferDTO);

    ScoringDataDTO createScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId);

}
