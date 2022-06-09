package com.neoflex.conveyor.models.add_services;

import com.neoflex.conveyor.models.credit.Credit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "add_services")
public class Add_services {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Boolean is_insurance_enabled; //(Страховка включена?)

    @Column
    private Boolean is_salary_client; //(Зарплатный клиент?)

//    //------------------------------------FOREIGN ENTITIES
//    @OneToOne(optional = false, mappedBy = "addServices")
//    public Credit credit;
}