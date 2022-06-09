package com.neoflex.conveyor.models.employment_status;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employment_status")
public class Employment_status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String employment_status;

}
