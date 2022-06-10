package com.neoflex.conveyor.models.employment;

import com.neoflex.conveyor.enums.EmploymentStatus;
import com.neoflex.conveyor.enums.Position;
import com.neoflex.conveyor.models.client.Client;
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

    @Column
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus; // (Рабочий статус)

    @Column
    private BigDecimal salary;// (зарплата)

    @Column
    private String EmployerINN;

    @Column
    @Enumerated(EnumType.STRING)
    private Position position;// (должность)

    @Column
    private Integer workExperienceTotal;// (общий опыт работы)

    @Column
    private Integer workExperienceCurrent;// (опыт работы на текущем месте)

    //    //------------------------------------FOREIGN ENTITIES
    @OneToOne(optional = false, mappedBy = "employment")
    public Client client;


}