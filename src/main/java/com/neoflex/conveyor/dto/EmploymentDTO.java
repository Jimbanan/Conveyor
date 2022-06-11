package com.neoflex.conveyor.dto;

import com.neoflex.conveyor.enums.EmploymentStatus;
import com.neoflex.conveyor.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность работника")
public class EmploymentDTO {

    private EmploymentStatus employmentStatus; //Рабочий статус
    private String employerINN; //ИНН работника
    private BigDecimal salary; //Зарплата
    private Position position; //Должность
    private Integer workExperienceTotal; //Общий стаж работы
    private Integer workExperienceCurrent; //Текущий стаж работы

}
