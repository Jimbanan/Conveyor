package com.neoflex.conveyor.services;

import com.neoflex.conveyor.dto.*;
import com.neoflex.conveyor.enums.Status;
import com.neoflex.conveyor.models.add_services.Add_services;
import com.neoflex.conveyor.models.application.Application;
import com.neoflex.conveyor.models.applicationStatusHistory.ApplicationStatusHistory;
import com.neoflex.conveyor.models.client.Client;
import com.neoflex.conveyor.models.credit.Credit;
import com.neoflex.conveyor.models.employment.Employment;
import com.neoflex.conveyor.models.passport.Passport;

public interface DealService {

    Long addClient(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void addOffer(LoanOfferDTO loanOfferDTO);

    ScoringDataDTO createScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId);

    void updateCredit(CreditDTO creditDTO, Long applicationId);

    Application getApplication(Long applicationId);

    void updateApplication(Application application, Status status);

    void updateApplication(Application application);

    Passport savePassport(LoanApplicationRequestDTO loanApplicationRequestDTO);

    Client saveClient(LoanApplicationRequestDTO loanApplicationRequestDTO, Passport passport);

    Employment saveEmployment(FinishRegistrationRequestDTO finishRegistrationRequestDTO);

    ApplicationStatusHistory addApplicationStatusHistory(Status status);

    Application saveApplication(Client client, Status status);

    Add_services addAddServices(LoanOfferDTO loanOfferDTO);

    Credit addCredit(LoanOfferDTO loanOfferDTO, Add_services addServices);


}
