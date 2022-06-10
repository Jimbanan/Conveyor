package com.neoflex.conveyor.models.employment_status;

import com.neoflex.conveyor.models.employment.Employment;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "employment_status")
public class Employment_status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String employment_status;

//    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "employmentStatus")
    public Employment employment;
}
