package com.neoflex.conveyor.models.gender;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "gender")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String gender;

}