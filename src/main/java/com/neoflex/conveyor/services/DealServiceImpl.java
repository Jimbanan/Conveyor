package com.neoflex.conveyor.services;

import com.neoflex.conveyor.dto.*;
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
import com.neoflex.conveyor.models.passport.Passport;
import com.neoflex.conveyor.models.passport.PassportRepository;
import com.neoflex.conveyor.models.paymentSchedule.PaymentSchedule;
import com.neoflex.conveyor.models.paymentSchedule.PaymentScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    private final PaymentScheduleRepository paymentScheduleRepository;

    public DealServiceImpl(@Autowired ClientRepository clientRepository,
                           @Autowired PassportRepository passportRepository,
                           @Autowired ApplicationRepository applicationRepository,
                           @Autowired CreditRepository creditRepository,
                           @Autowired Add_serivesRepository addServesRepository,
                           @Autowired EmploymentRepository employmentRepository,
                           @Autowired ApplicationStatusHistoryRepository applicationStatusHistoryRepository,
                           @Autowired PaymentScheduleRepository paymentScheduleRepository) {
        this.clientRepository = clientRepository;
        this.passportRepository = passportRepository;
        this.applicationRepository = applicationRepository;
        this.creditRepository = creditRepository;
        this.addServesRepository = addServesRepository;
        this.employmentRepository = employmentRepository;
        this.applicationStatusHistoryRepository = applicationStatusHistoryRepository;
        this.paymentScheduleRepository = paymentScheduleRepository;
    }

    @Override
    public Long addClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("addClient() - Long: {}", saveApplication(saveClient(loanApplicationRequestDTO, savePassport(loanApplicationRequestDTO)), Status.PREAPPROVAL));
        Application application = saveApplication(saveClient(loanApplicationRequestDTO, savePassport(loanApplicationRequestDTO)), Status.PREAPPROVAL);
        return application.getId();
    }

    @Override
    public void addOffer(LoanOfferDTO loanOfferDTO) {

        Application application = getApplication(loanOfferDTO.getApplicationId());
        application.setCredit(addCredit(loanOfferDTO, addAddServices(loanOfferDTO)));
        application.setAppliedOffer(loanOfferDTO.getApplicationId());
        application.setSign_date(LocalDate.now());
        applicationRepository.save(application);
        log.info("addOffer() - void: Информация о выбранном офере добавлена в базу данных");
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
        log.info("createScoringDataDTO() - ScoringDataDTO: Информация о Application обновлена в БД");

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

    @Override
    public void updateCredit(CreditDTO creditDTO, Long applicationId) {
        List<PaymentSchedule> paymentSchedules = new ArrayList<>();
        log.info("updateCredit() - void:  List<PaymentSchedule> paymentSchedules - Создан");

        for (int i = 0; i < creditDTO.getPaymentSchedule().size(); i++) {
            paymentSchedules.add(paymentScheduleRepository.save(PaymentSchedule.builder()
                    .number(creditDTO.getPaymentSchedule().get(i).getNumber())
                    .date(creditDTO.getPaymentSchedule().get(i).getDate())
                    .totalPayment(creditDTO.getPaymentSchedule().get(i).getTotalPayment())
                    .interestPayment(creditDTO.getPaymentSchedule().get(i).getInterestPayment())
                    .debtPayment(creditDTO.getPaymentSchedule().get(i).getDebtPayment())
                    .remainingDebt(creditDTO.getPaymentSchedule().get(i).getRemainingDebt())
                    .build()));
        }
        log.info("updateCredit() - void:  List<PaymentSchedule> paymentSchedules - Заполнен и добавлен в БД");

        Application application = getApplication(applicationId);
        application.setStatus(Status.APPROVED);
        application.getCredit().setAmount(creditDTO.getAmount());
        application.getCredit().setTerm(creditDTO.getTerm());
        application.getCredit().setMonthlyPayment(creditDTO.getMonthlyPayment());
        application.getCredit().setRate(creditDTO.getRate());
        application.getCredit().setPsk(creditDTO.getPsk());
        application.getCredit().getAddServices().setIs_insurance_enabled(creditDTO.getIsInsuranceEnabled());
        application.getCredit().getAddServices().setIs_salary_client(creditDTO.getIsSalaryClient());
        application.getCredit().setPayment_schedule(paymentSchedules);
        updateApplication(application, Status.APPROVED);
        log.info("updateCredit() - void: Информация о Application обновлена в БД");

    }

    private Application getApplication(Long applicationId) {
        log.info("getApplication() - Application: запрос для application.id = {}", applicationId);
        return applicationRepository.findById(applicationId).orElseThrow(()
                -> new NoSuchElementException("with id='" + applicationId + "' does not exist"));
    }

    @Transactional
    public void updateApplication(Application application, Status status) {
        List<ApplicationStatusHistory> list = new ArrayList<>();
        list.add(addApplicationStatusHistory(status));

        application.getStatus_history().add(list.get(0));
        log.info("updateApplication() - void: Информация о application.status_history добавлена");

        applicationRepository.save(application);
        log.info("updateApplication() - void: Информация о Application обновлена в БД");
    }

    private void updateApplication(Application application) {
        applicationRepository.save(application);
        log.info("updateApplication() - void: Информация о Application обновлена в БД");
    }

    private Passport savePassport(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        Passport passport = new Passport(loanApplicationRequestDTO.getPassportSeries(), loanApplicationRequestDTO.getPassportNumber());
        log.info("savePassport() - Passport: Информация о Passport добавлена в БД");
        return passportRepository.save(passport);
    }

    private Client saveClient(LoanApplicationRequestDTO loanApplicationRequestDTO, Passport passport) {
        log.info("saveClient() - Client: Информация о Client добавлена в БД");
        return clientRepository.save(Client.builder()
                .lastName(loanApplicationRequestDTO.getLastName())
                .firstName(loanApplicationRequestDTO.getFirstName())
                .middleName(loanApplicationRequestDTO.getMiddleName())
                .birthdate(loanApplicationRequestDTO.getBirthdate())
                .email(loanApplicationRequestDTO.getEmail())
                .passport(passport)
                .build());
    }

    private Employment saveEmployment(FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        log.info("saveEmployment() - Employment: Информация о Employment добавлена в БД");
        return employmentRepository.save(Employment.builder()
                .employmentStatus(finishRegistrationRequestDTO.getEmployment().getEmploymentStatus())
                .EmployerINN(finishRegistrationRequestDTO.getEmployment().getEmployerINN())
                .salary(finishRegistrationRequestDTO.getEmployment().getSalary())
                .position(finishRegistrationRequestDTO.getEmployment().getPosition())
                .workExperienceTotal(finishRegistrationRequestDTO.getEmployment().getWorkExperienceTotal())
                .workExperienceCurrent(finishRegistrationRequestDTO.getEmployment().getWorkExperienceCurrent())
                .build());
    }

    private ApplicationStatusHistory addApplicationStatusHistory(Status status) {
        log.info("addApplicationStatusHistory() - ApplicationStatusHistory: Информация о ApplicationStatusHistory добавлена в БД");
        return applicationStatusHistoryRepository.save(ApplicationStatusHistory.builder()
                .status(status)
                .time(LocalDateTime.now())
                .build());
    }

    private Application saveApplication(Client client, Status status) {
        log.info("saveApplication() - Long: Информация о Application добавлена в БД");
        return applicationRepository.save(Application.builder()
                .client(client)
                .creation_date(LocalDate.now())
                .status(status)
                .status_history(Arrays.asList(addApplicationStatusHistory(status)))
                .build());
    }

    private Add_services addAddServices(LoanOfferDTO loanOfferDTO) {
        log.info("addAddServices() - Add_services: Информация о Add_services добавлена в БД");
        return addServesRepository.save(Add_services.builder()
                .is_insurance_enabled(loanOfferDTO.getIsInsuranceEnabled())
                .is_salary_client(loanOfferDTO.getIsSalaryClient())
                .build());
    }

    private Credit addCredit(LoanOfferDTO loanOfferDTO, Add_services addServices) {
        log.info("addCredit() - Credit: Информация о Credit добавлена в БД");
        return creditRepository.save(Credit.builder()
                .amount(loanOfferDTO.getRequestedAmount())
                .term(loanOfferDTO.getTerm())
                .monthlyPayment(loanOfferDTO.getMonthlyPayment())
                .rate(loanOfferDTO.getRate())
                .psk(loanOfferDTO.getTotalAmount())
                .addServices(addServices)
                .credit_status(Credit_status.CALCULATED)
                .build());
    }
}
