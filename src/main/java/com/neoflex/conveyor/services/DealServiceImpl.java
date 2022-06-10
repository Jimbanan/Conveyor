package com.neoflex.conveyor.services;

import com.neoflex.conveyor.dto.LoanApplicationRequestDTO;
import com.neoflex.conveyor.dto.LoanOfferDTO;
import com.neoflex.conveyor.enums.Credit_status;
import com.neoflex.conveyor.enums.Status;
import com.neoflex.conveyor.models.add_services.Add_serivesRepository;
import com.neoflex.conveyor.models.add_services.Add_services;
import com.neoflex.conveyor.models.application.Application;
import com.neoflex.conveyor.models.application.ApplicationRepository;
import com.neoflex.conveyor.models.applicationStatusHistory.ApplicationStatusHistory;
import com.neoflex.conveyor.models.applicationStatusHistory.ApplicationStatusHistoryRepository;
import com.neoflex.conveyor.models.client.Client;
import com.neoflex.conveyor.models.client.ClientRepository;
import com.neoflex.conveyor.models.credit.Credit;
import com.neoflex.conveyor.models.credit.CreditRepository;
import com.neoflex.conveyor.models.pasport.Passport;
import com.neoflex.conveyor.models.pasport.PassportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private ApplicationStatusHistoryRepository applicationStatusHistoryRepository;

    public Long addClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Passport passport = savePassport(loanApplicationRequestDTO);

        Client client = saveClient(loanApplicationRequestDTO, passport);

        return saveApplication(client);
    }


    //TODO переделать в нормальный вид
    @Override
    public void addOffer(LoanOfferDTO loanOfferDTO) {

        Add_services addServices = addAddServices(loanOfferDTO);

        Credit credit = addCredit(loanOfferDTO, addServices);


        Optional<Application> application = applicationRepository.findById(loanOfferDTO.getApplicationId());
        ArrayList<Application> applications = new ArrayList<>();
        application.ifPresent(applications::add);

        System.out.println(applications.get(0).getId());

        //TODO Сделать изменение статуса заявки
        applications.get(0).setCredit(credit);
        applications.get(0).setAppliedOffer(loanOfferDTO.getApplicationId());
        applications.get(0).setSign_date(LocalDate.now());
        applicationRepository.save(applications.get(0));

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

    private ApplicationStatusHistory addApplicationStatusHistory(Status status) {
        ApplicationStatusHistory applicationStatusHistory = new ApplicationStatusHistory();
        applicationStatusHistory.setStatus(status);
        applicationStatusHistory.setTime(LocalDateTime.now());
        return applicationStatusHistoryRepository.save(applicationStatusHistory);
    }

    private Long saveApplication(Client client) {
        Application application = new Application(client);
        application.setCreation_date(LocalDate.now());
        application.setStatus(Status.PREAPPROVAL);
        application.setStatus_history(addApplicationStatusHistory(Status.PREAPPROVAL));
        applicationRepository.save(application);
        return application.getId();
    }

    private Add_services addAddServices(LoanOfferDTO loanOfferDTO) {
        Add_services addServices = new Add_services();
        addServices.setIs_insurance_enabled(loanOfferDTO.getIsInsuranceEnabled());
        addServices.setIs_salary_client(loanOfferDTO.getIsSalaryClient());
        return addSerivesRepository.save(addServices);
    }

    private Credit addCredit(LoanOfferDTO loanOfferDTO, Add_services addServices) {
        Credit credit = new Credit();

        credit.setAmount(loanOfferDTO.getRequestedAmount());
        credit.setTerm(loanOfferDTO.getTerm());
        credit.setMonthlyPayment(loanOfferDTO.getMonthlyPayment());
        credit.setRate(loanOfferDTO.getRate());
        credit.setPsk(loanOfferDTO.getTotalAmount());
        credit.setAddServices(addServices);
        credit.setCredit_status(Credit_status.CALCULATED);

        creditRepository.save(credit);
        return credit;
    }
}
