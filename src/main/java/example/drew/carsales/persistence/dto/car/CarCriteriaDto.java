package example.drew.carsales.persistence.dto.car;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class CarCriteriaDto {

    public static final String DEFAULT_SORT_CRITERIA = "createdAt";

    public static final String DEFAULT_ORDER_CRITERIA = "desc";

    @Size(max = 255)
    private String brand;

    @Size(max = 255)
    private String model;

    @Size(max = 255)
    private String username;

    @Positive
    @Max(value = Long.MAX_VALUE)
    private Long kilometers;

    private String build;

    @NotBlank
    @Size(max = 255)
    private String sort = DEFAULT_SORT_CRITERIA;

    @NotBlank
    @Size(max = 4)
    private String order = DEFAULT_ORDER_CRITERIA;

}
