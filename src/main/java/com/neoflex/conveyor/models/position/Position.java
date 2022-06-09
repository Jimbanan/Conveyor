package com.neoflex.conveyor.models.position;

import com.neoflex.conveyor.models.employment.Employment;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String position;

//    //------------------------------------FOREIGN ENTITIES
//    @OneToOne(optional = false, mappedBy = "position")
//    public Employment employment;
}