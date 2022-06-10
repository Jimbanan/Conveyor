package com.neoflex.conveyor.services;

import com.neoflex.conveyor.dto.*;

public interface DealService {

    Long addClient(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void addOffer(LoanOfferDTO loanOfferDTO);

    ScoringDataDTO createScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId);

    void updateCredit(CreditDTO creditDTO, Long applicationId);
}
