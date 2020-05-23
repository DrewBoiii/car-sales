package example.drew.carsales.persistence.dto.car;

import example.drew.carsales.persistence.entity.User;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class SaveCarDto {

    @Size(max = 255)
    @NotBlank
    private String brand;

    @Size(max = 255)
    @NotBlank
    private String model;

    private User user;

    @Positive
    @Max(value = Long.MAX_VALUE)
    @NotBlank
    private Long kilometers;

    private String description;

    @NotBlank
    private String build;

}
