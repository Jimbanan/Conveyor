package com.neoflex.conveyor.models.marital_status;

import com.neoflex.conveyor.models.client.Client;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "marital_status")
public class Marital_status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String marital_status;

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "maritalStatus")
    public Client client;

}