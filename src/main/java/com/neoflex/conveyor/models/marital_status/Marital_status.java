package com.neoflex.conveyor.models.marital_status;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "marital_status")
public class Marital_status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String marital_status;

}