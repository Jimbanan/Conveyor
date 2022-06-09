package com.neoflex.conveyor.models.position;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String position;

}