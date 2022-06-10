package com.neoflex.conveyor.models.passport;

import com.neoflex.conveyor.models.client.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "passport")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String passportSeries;

    @Column
    private String passportNumber;

    @Column
    private LocalDate passportIssueDate;

    @Column
    private String passportIssueBranch;

    public Passport(String passportSeries, String passportNumber) {
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
    }

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "passport")
    public Client client;
}