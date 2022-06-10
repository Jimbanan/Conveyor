package com.neoflex.conveyor.services;

import com.neoflex.conveyor.dto.FinishRegistrationRequestDTO;
import com.neoflex.conveyor.dto.LoanApplicationRequestDTO;
import com.neoflex.conveyor.dto.LoanOfferDTO;
import com.neoflex.conveyor.dto.ScoringDataDTO;
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
import com.neoflex.conveyor.models.employment.Employment;
import com.neoflex.conveyor.models.employment.EmploymentRepository;
import com.neoflex.conveyor.models.pasport.Passport;
import com.neoflex.conveyor.models.pasport.PassportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class DealServiceImpl implements DealService {

    private final ClientRepository clientRepository;
    private final PassportRepository passportRepository;
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;
    private final Add_serivesRepository addServesRepository;
    private final EmploymentRepository employmentRepository;
    private final ApplicationStatusHistoryRepository applicationStatusHistoryRepository;

    public DealServiceImpl(@Autowired ClientRepository clientRepository,
                           @Autowired PassportRepository passportRepository,
                           @Autowired ApplicationRepository applicationRepository,
                           @Autowired CreditRepository creditRepository,
                           @Autowired Add_serivesRepository addServesRepository,
                           @Autowired EmploymentRepository employmentRepository,
                           @Autowired ApplicationStatusHistoryRepository applicationStatusHistoryRepository) {
        this.clientRepository = clientRepository;
        this.passportRepository = passportRepository;
        this.applicationRepository = applicationRepository;
        this.creditRepository = creditRepository;
        this.addServesRepository = addServesRepository;
        this.employmentRepository = employmentRepository;
        this.applicationStatusHistoryRepository = applicationStatusHistoryRepository;
    }

    @Override
    public Long addClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return saveApplication(saveClient(loanApplicationRequestDTO, savePassport(loanApplicationRequestDTO)));
    }

    @Override
    public void addOffer(LoanOfferDTO loanOfferDTO) {
        Application application = getApplication(loanOfferDTO.getApplicationId());
        application.setCredit(addCredit(loanOfferDTO, addAddServices(loanOfferDTO)));
        application.setAppliedOffer(loanOfferDTO.getApplicationId());
        application.setSign_date(LocalDate.now());
        applicationRepository.save(application);
    }

    @Override
    public ScoringDataDTO createScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId) {

        Application application = getApplication(applicationId);
        application.getClient().setGender(finishRegistrationRequestDTO.getGenders());
        application.getClient().setMarital_status(finishRegistrationRequestDTO.getMaritalStatus());
        application.getClient().setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        application.getClient().getPassport().setPassportIssueDate(finishRegistrationRequestDTO.getPassportIssueDate());
        application.getClient().getPassport().setPassportIssueBranch(finishRegistrationRequestDTO.getPassportIssueBrach());
        application.getClient().setEmployment(saveEmployment(finishRegistrationRequestDTO));
        application.getClient().setAccount(finishRegistrationRequestDTO.getAccount());

        updateApplication(application);

        return ScoringDataDTO.builder()
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .firstName(application.getClient().getFirstName())
                .lastName(application.getClient().getLastName())
                .middleName(application.getClient().getMiddleName())
                .gender(application.getClient().getGender())
                .birthdate(application.getClient().getBirthdate())
                .passportSeries(application.getClient().getPassport().getPassportSeries())
                .passportNumber(application.getClient().getPassport().getPassportNumber())
                .passportIssueDate(application.getClient().getPassport().getPassportIssueDate())
                .passportIssueBranch(application.getClient().getPassport().getPassportIssueBranch())
                .maritalStatus(application.getClient().getMarital_status())
                .dependentAmount(application.getClient().getDependentAmount())
                .employment(finishRegistrationRequestDTO.getEmployment())
                .account(application.getClient().getAccount())
                .isInsuranceEnabled(application.getCredit().getAddServices().getIs_insurance_enabled())
                .isSalaryClient(application.getCredit().getAddServices().getIs_salary_client())
                .build();

    }

    private Application getApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(()
                -> new NoSuchElementException("with id='" + applicationId + "' does not exist"));
    }

    private void updateApplication(Application application) {
        applicationRepository.save(application);
    }

    private Passport savePassport(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        Passport passport = new Passport(loanApplicationRequestDTO.getPassportSeries(), loanApplicationRequestDTO.getPassportNumber());
        return passportRepository.save(passport);
    }

    private Client saveClient(LoanApplicationRequestDTO loanApplicationRequestDTO, Passport passport) {
        Client client = new Client();
        client.setLastName(loanApplicationRequestDTO.getLastName());
        client.setFirstName(loanApplicationRequestDTO.getFirstName());
        client.setMiddleName(loanApplicationRequestDTO.getMiddleName());
        client.setBirthdate(loanApplicationRequestDTO.getBirthdate());
        client.setEmail(loanApplicationRequestDTO.getEmail());
        client.setPassport(passport);
        return clientRepository.save(client);
    }

    private Employment saveEmployment(FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        Employment employment = new Employment();
        employment.setEmploymentStatus(finishRegistrationRequestDTO.getEmployment().getEmploymentStatus());
        employment.setEmployerINN(finishRegistrationRequestDTO.getEmployment().getEmployerINN());
        employment.setSalary(finishRegistrationRequestDTO.getEmployment().getSalary());
        employment.setPosition(finishRegistrationRequestDTO.getEmployment().getPosition());
        employment.setWorkExperienceTotal(finishRegistrationRequestDTO.getEmployment().getWorkExperienceTotal());
        employment.setWorkExperienceCurrent(finishRegistrationRequestDTO.getEmployment().getWorkExperienceCurrent());
        return employmentRepository.save(employment);
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
        return addServesRepository.save(addServices);
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
        return creditRepository.save(credit);
    }
}
