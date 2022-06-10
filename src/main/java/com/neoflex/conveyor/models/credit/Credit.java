package com.neoflex.conveyor.models.credit;

import com.neoflex.conveyor.enums.Credit_status;
import com.neoflex.conveyor.models.add_services.Add_services;
import com.neoflex.conveyor.models.application.Application;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addServices_id", unique = true, updatable = false)
    private Add_services addServices;// (доп услуги)

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "credit_status_id", unique = true, updatable = false)

    @Column
    @Enumerated(EnumType.STRING)
    private Credit_status credit_status; //(Статус кредита)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "credit")
    public Application application;
}