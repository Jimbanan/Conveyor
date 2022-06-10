package com.neoflex.conveyor.models.application;

import com.neoflex.conveyor.enums.Status;
import com.neoflex.conveyor.models.applicationStatusHistory.ApplicationStatusHistory;
import com.neoflex.conveyor.models.client.Client;
import com.neoflex.conveyor.models.credit.Credit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "client_id", unique = true)
    private Client client; // (Клиент)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id", unique = true)
    private Credit credit; // (Кредит)

    @Column
    @Enumerated(EnumType.STRING)
    private Status status; // (Статус)

    @Column
    private LocalDate creation_date; // (Дата создания)

    @Column
    private Long appliedOffer; // (Принятое предложение кредита)

    @Column
    private LocalDate sign_date; // (Дата подписания)

    @Column
    private String ses_code; // (Код ПЭП (Простая Электронная Подпись))

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    private List<ApplicationStatusHistory> status_history; //(История изменения статусов)

    public Application(Client client) {
        this.client = client;
    }

}