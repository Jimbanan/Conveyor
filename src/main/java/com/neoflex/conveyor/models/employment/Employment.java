package com.neoflex.conveyor.models.employment;

import com.neoflex.conveyor.models.client.Client;
import com.neoflex.conveyor.models.employment_status.Employment_status;
import com.neoflex.conveyor.models.position.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "employment")
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "employmentStatus_id", unique = true, nullable = false, updatable = false)
    private Employment_status employmentStatus; // (Рабочий статус)

    @Column
    private BigDecimal salary;// (зарплата)

    @OneToOne(optional = false)
    @JoinColumn(name = "position_id", unique = true, nullable = false, updatable = false)
    private Position position;// (должность)

    @Column
    private Integer workExperienceTotal;// (общий опыт работы)

    @Column
    private Integer workExperienceCurrent;// (опыт работы на текущем месте)

    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "passport")
    public Client client;

}