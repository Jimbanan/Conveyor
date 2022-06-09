package com.neoflex.conveyor.services;

import com.neoflex.conveyor.dto.LoanApplicationRequestDTO;
import com.neoflex.conveyor.dto.LoanOfferDTO;
import com.neoflex.conveyor.models.application.Application;
import com.neoflex.conveyor.models.application.ApplicationRepository;
import com.neoflex.conveyor.models.client.Client;
import com.neoflex.conveyor.models.client.ClientRepository;
import com.neoflex.conveyor.models.pasport.Passport;
import com.neoflex.conveyor.models.pasport.PassportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DealServiceImpl implements DealService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public void addClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Passport passport = savePassport(loanApplicationRequestDTO);

        Client client = saveClient(loanApplicationRequestDTO, passport);

        saveApplication(client);

    }

    @Override
    public void addOffer(LoanOfferDTO loanOfferDTO) {



    }

    private Passport savePassport(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Passport passport = new Passport(loanApplicationRequestDTO.getPassportSeries(), loanApplicationRequestDTO.getPassportNumber());

        passportRepository.save(passport);
        return passport;
    }

    private Client saveClient(LoanApplicationRequestDTO loanApplicationRequestDTO, Passport passport) {

        Client client = new Client(loanApplicationRequestDTO.getLastName(), loanApplicationRequestDTO.getFirstName(),
                loanApplicationRequestDTO.getMiddleName(), loanApplicationRequestDTO.getBirthdate(), loanApplicationRequestDTO.getEmail(),
                passport);

        clientRepository.save(client);
        return client;
    }

    private void saveApplication(Client client) {
        Application application = new Application(client);
        applicationRepository.save(application);
    }

}
