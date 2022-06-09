package com.neoflex.conveyor.services;

import com.neoflex.conveyor.dto.LoanApplicationRequestDTO;
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

    public void addClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {


        Passport passport = new Passport(loanApplicationRequestDTO.getPassportSeries(), loanApplicationRequestDTO.getPassportNumber());

        passportRepository.save(passport);

        Client client = new Client(loanApplicationRequestDTO.getLastName(), loanApplicationRequestDTO.getFirstName(),
                loanApplicationRequestDTO.getMiddleName(), loanApplicationRequestDTO.getBirthdate(), loanApplicationRequestDTO.getEmail(),
                passport);

//        addPassport(loanApplicationRequestDTO.getPassportSeries(), loanApplicationRequestDTO.getPassportNumber());

        clientRepository.save(client);
        System.out.println(client.getId());

    }

    private Long addPassport(String passportSeries, String passportNumber) {

        Passport passport = new Passport(passportSeries, passportNumber);

        passportRepository.save(passport);
        return passport.getId();
    }

}
