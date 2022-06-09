package com.neoflex.conveyor.models.status;

import com.neoflex.conveyor.models.application.Application;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String status;

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "status")
    public Application application;
}