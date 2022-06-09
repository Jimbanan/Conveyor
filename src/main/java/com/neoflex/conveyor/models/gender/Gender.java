package com.neoflex.conveyor.models.gender;

import com.neoflex.conveyor.models.client.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "gender")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String gender;

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "gender")
    public Client client;
}