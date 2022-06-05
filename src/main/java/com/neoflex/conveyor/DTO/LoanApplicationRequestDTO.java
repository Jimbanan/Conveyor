package com.neoflex.conveyor.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Schema(description = "Сущность первоначальных данных пользователя")
public class LoanApplicationRequestDTO {

    @DecimalMin(value = "10000", message = "Минимальная сумма кредита: 10000")
    private BigDecimal amount;

    @Min(value = 6, message = "Минимальный срок кредита: 6 месяцев")
    private Integer term;

    @Size(min = 2, max = 30, message = "Длина имени: 2-30 символов")
    private String firstName;

    @Size(min = 2, max = 30, message = "Длина фамилии: 2-30 символов")
    private String lastName;

    @Size(min = 2, max = 30, message = "Длина отчества: 2-30 символов")
    private String middleName;

    @Pattern(regexp = "[\\w.]{2,50}@[\\w.]{2,20}")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Дата рождения не должна быть больше текущей")
    private LocalDate birthdate;

    @Size(min = 4, max = 4, message = "Длина серии паспорта: 4 символа")
    private String passportSeries;
    @Size(min = 6, max = 6, message = "Длина номера паспорта: 6 символов")
    private String passportNumber;

}
