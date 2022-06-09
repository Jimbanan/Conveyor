package com.neoflex.conveyor.models.application;

import com.neoflex.conveyor.models.client.Client;
import com.neoflex.conveyor.models.credit.Credit;
import com.neoflex.conveyor.models.status.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "client_id", unique = true, updatable = false)
    private Client client; // (Клиент)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id", unique = true, updatable = false)
    private Credit credit; // (Кредит)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", unique = true, updatable = false)
    private Status status; // (Статус)

    @Column
    private LocalDate creation_date; // (Дата создания)

    @Column
    private Long appliedOffer; // (Принятое предложение кредита)

    @Column
    private LocalDate sign_date; // (Дата подписания)

    @Column
    private String ses_code; // (Код ПЭП (Простая Электронная Подпись))

    //List<ApplicationStatusHistoryDTO> status_history (История изменения статусов)

    public Application(Client client) {
        this.client = client;
    }

}