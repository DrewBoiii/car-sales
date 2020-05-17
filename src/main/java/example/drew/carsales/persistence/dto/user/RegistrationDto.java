package example.drew.carsales.persistence.dto.user;

import example.drew.carsales.util.PasswordUtil;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegistrationDto {

    @Min(value = 4, message = "Username should be at least 4 characters long.")
    @Max(value = 255)
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @Pattern(regexp = PasswordUtil.PASSWORD_PATTERN, message = "Weak password.")
    @NotBlank
    private String password;

    @Pattern(regexp = PasswordUtil.PASSWORD_PATTERN, message = "Weak password.")
    @NotBlank
    private String confirm;

}
