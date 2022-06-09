package com.neoflex.conveyor.models.client;

import com.neoflex.conveyor.dto.EmploymentDTO;
import com.neoflex.conveyor.enums.EmploymentStatus;
import com.neoflex.conveyor.enums.Genders;
import com.neoflex.conveyor.enums.MaritalStatus;
import com.neoflex.conveyor.enums.Position;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String lastName;// (Фамилия)
    private String firstName;// (Имя)
    private String middleName;// (Отчество)
    private LocalDate birthdate;// (Дата рождения)
    private String email;// (Email адрес)
    private Genders gender;// (Пол)
    private MaritalStatus maritalStatus;// (Семейное положение)

    private Integer dependentAmount;// (Количество иждивенцев)

     //    passport (Паспорт)

    private String passportSeries;// (серия)
    private String passportNumber;// (номер)
    private LocalDate passportIssueDate;// (дата выдачи)
    private String passportIssueBranch;// (отделение)
//    private EmploymentDTO employment;// (Работа)
    private EmploymentStatus employmentStatus; // (Рабочий статус)

    private BigDecimal salary;// (зарплата)
    private Position position;// (должность)

    private Integer workExperienceTotal;// (общий опыт работы)
    private Integer workExperienceCurrent;// (опыт работы на текущем месте)
    private String account;// (Счет клиента)
//
}