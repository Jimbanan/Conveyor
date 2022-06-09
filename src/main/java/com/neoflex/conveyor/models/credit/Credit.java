package com.neoflex.conveyor.models.credit;

import com.neoflex.conveyor.models.add_services.Add_services;
import com.neoflex.conveyor.models.application.Application;
import com.neoflex.conveyor.models.credit_status.Credit_status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private BigDecimal amount; // (Сумма)

    @Column
    private Integer term; // (Срок)

    @Column
    private BigDecimal monthlyPayment; // (Ежемесячный платеж)

    @Column
    private BigDecimal rate; // (Процентная ставка)

    @Column
    private BigDecimal psk; // (Полная стоимость кредита)

//    private List<PaymentScheduleElement> payment_schedule; (График платежей)

    //    @OneToOne(optional = false)
//    @JoinColumn(name = "addServices_id", unique = true, nullable = false, updatable = false)
//    private Add_services addServices;// (Пол)
//
//    @OneToOne(optional = false)
//    @JoinColumn(name = "credit_status_id", unique = true, nullable = false, updatable = false)
//    private Credit_status credit_status; //(Статус кредита)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "credit")
    public Application application;
}