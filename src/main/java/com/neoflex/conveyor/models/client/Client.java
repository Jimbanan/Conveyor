package com.neoflex.conveyor.models.client;

import com.neoflex.conveyor.dto.EmploymentDTO;
import com.neoflex.conveyor.enums.EmploymentStatus;
import com.neoflex.conveyor.enums.Genders;
import com.neoflex.conveyor.enums.MaritalStatus;
import com.neoflex.conveyor.enums.Position;
import com.neoflex.conveyor.models.application.Application;
import com.neoflex.conveyor.models.employment.Employment;
import com.neoflex.conveyor.models.gender.Gender;
import com.neoflex.conveyor.models.marital_status.Marital_status;
import com.neoflex.conveyor.models.pasport.Passport;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String lastName;// (Фамилия)

    @Column
    private String firstName;// (Имя)

    @Column
    private String middleName;// (Отчество)

    @Column
    private LocalDate birthdate;// (Дата рождения)

    @Column
    private String email;// (Email адрес)

    @OneToOne(optional = false)
    @JoinColumn(name = "gender_id", unique = true, nullable = false, updatable = false)
    private Gender gender;// (Пол)

    @OneToOne(optional = false)
    @JoinColumn(name = "maritalStatus_id", unique = true, nullable = false, updatable = false)
    private Marital_status maritalStatus;// (Семейное положение)

    @Column
    private Integer dependentAmount;// (Количество иждивенцев)

    @OneToOne(optional = false)
    @JoinColumn(name = "passport_id", unique = true, nullable = false, updatable = false)
    private Passport passport;

    @OneToOne(optional = false)
    @JoinColumn(name = "employment_id", unique = true, nullable = false, updatable = false)
    private Employment employment;// (Работа)

    @Column
    private String account;// (Счет клиента)

    public Client(String lastName, String firstName, String middleName, LocalDate birthdate, String email, Passport passport) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.email = email;
        this.passport = passport;
    }

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "client")
    public Application application;
}