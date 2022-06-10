package com.neoflex.conveyor.services;

import com.neoflex.conveyor.dto.LoanApplicationRequestDTO;
import com.neoflex.conveyor.dto.LoanOfferDTO;
import com.neoflex.conveyor.models.add_services.Add_serivesRepository;
import com.neoflex.conveyor.models.add_services.Add_services;
import com.neoflex.conveyor.models.application.Application;
import com.neoflex.conveyor.models.application.ApplicationRepository;
import com.neoflex.conveyor.models.client.Client;
import com.neoflex.conveyor.models.client.ClientRepository;
import com.neoflex.conveyor.models.credit.Credit;
import com.neoflex.conveyor.models.credit.CreditRepository;
import com.neoflex.conveyor.models.credit_status.Credit_status;
import com.neoflex.conveyor.models.credit_status.Credit_statusRepository;
import com.neoflex.conveyor.models.pasport.Passport;
import com.neoflex.conveyor.models.pasport.PassportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class DealServiceImpl implements DealService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private ApplicationRepository applicationRepository;


    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private Add_serivesRepository addSerivesRepository;

    @Autowired
    private Credit_statusRepository creditStatusRepository;

    public Long addClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Passport passport = savePassport(loanApplicationRequestDTO);

        Client client = saveClient(loanApplicationRequestDTO, passport);

        return saveApplication(client);
    }


    //TODO переделать в нормальный вид
    @Override
    public void addOffer(LoanOfferDTO loanOfferDTO) {

        Add_services addServices = new Add_services();
        addServices.setIs_insurance_enabled(loanOfferDTO.getIsInsuranceEnabled());
        addServices.setIs_salary_client(loanOfferDTO.getIsSalaryClient());

        addSerivesRepository.save(addServices);


        Credit_status creditStatus = new Credit_status();
        creditStatus.setCredit_status("CALCULATED");
        creditStatusRepository.save(creditStatus);


        Credit credit = new Credit();

        credit.setAmount(loanOfferDTO.getRequestedAmount());
        credit.setTerm(loanOfferDTO.getTerm());
        credit.setMonthlyPayment(loanOfferDTO.getMonthlyPayment());
        credit.setRate(loanOfferDTO.getRate());
        credit.setPsk(loanOfferDTO.getTotalAmount());
        credit.setAddServices(addServices);
        credit.setCredit_status(creditStatus);

        creditRepository.save(credit);


        Optional<Application> application = applicationRepository.findById(loanOfferDTO.getApplicationId());
        ArrayList<Application> applications = new ArrayList<>();
        application.ifPresent(applications::add);

        System.out.println(applications);

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

    private Long saveApplication(Client client) {
        Application application = new Application(client);
        applicationRepository.save(application);
        return application.getId();
    }

}
