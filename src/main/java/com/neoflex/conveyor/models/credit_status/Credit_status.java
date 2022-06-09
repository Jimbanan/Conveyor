package com.neoflex.conveyor.models.credit_status;

import com.neoflex.conveyor.models.credit.Credit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "credit_status")
public class Credit_status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String credit_status;

//    //------------------------------------FOREIGN ENTITIES
//    @OneToOne(optional = false, mappedBy = "credit_status")
//    public Credit credit;
}