package com.neoflex.conveyor.models.applicationStatusHistory;

import com.neoflex.conveyor.enums.Status;
import com.neoflex.conveyor.models.application.Application;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "application_status_history")
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private LocalDateTime time;

    //------------------------------------FOREIGN ENTITIES
    //TODO @OneToMany
    @OneToOne(optional = false, mappedBy = "status_history")
    public Application application;

}