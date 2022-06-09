package com.neoflex.conveyor.dto;

import com.neoflex.conveyor.enums.Themes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

//TODO Поменять название сущности
public class EmailMessageDTO {

    private String address;
    private Themes theme;
    private Long applicationId;

}
