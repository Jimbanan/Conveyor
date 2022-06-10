package com.neoflex.conveyor.dto;

import com.neoflex.conveyor.enums.Themes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Сущность электронного письма")
public class EmailMessageDTO {

    private String address;
    private Themes theme;
    private Long applicationId;

}
