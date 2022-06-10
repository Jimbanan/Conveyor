package com.neoflex.conveyor.models.client;

import com.neoflex.conveyor.enums.Genders;
import com.neoflex.conveyor.enums.MaritalStatus;
import com.neoflex.conveyor.models.application.Application;
import com.neoflex.conveyor.models.employment.Employment;
import com.neoflex.conveyor.models.passport.Passport;
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

    @Column
    @Enumerated(EnumType.STRING)
    private Genders gender;// (Пол)

    @Column
    @Enumerated(EnumType.STRING)
    private MaritalStatus marital_status;// (Семейное положение)

    @Column
    private Integer dependentAmount;// (Количество иждивенцев)

    @OneToOne(optional = false)
    @JoinColumn(name = "passport_id", unique = true, nullable = false)
    private Passport passport;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id", unique = true)
    private Employment employment;// (Работа)

    @Column
    private String account;// (Счет клиента)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "client")
    public Application application;
}