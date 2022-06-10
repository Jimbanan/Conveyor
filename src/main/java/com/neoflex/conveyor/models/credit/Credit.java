package com.neoflex.conveyor.models.credit;

import com.neoflex.conveyor.enums.Credit_status;
import com.neoflex.conveyor.models.add_services.Add_services;
import com.neoflex.conveyor.models.application.Application;
import com.neoflex.conveyor.models.paymentSchedule.PaymentSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addServices_id", unique = true, updatable = false)
    private Add_services addServices;// (доп услуги)

    @OneToMany()
    @JoinColumn(name = "paymentSchedules_id")
    private List<PaymentSchedule> payment_schedule; //(График платежей)

    @Column
    @Enumerated(EnumType.STRING)
    private Credit_status credit_status; //(Статус кредита)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "credit")
    public Application application;
}