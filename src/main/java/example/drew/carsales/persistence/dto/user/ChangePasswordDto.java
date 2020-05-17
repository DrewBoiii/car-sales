package example.drew.carsales.persistence.dto.user;

import example.drew.carsales.util.PasswordUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String old;

    @Pattern(regexp = PasswordUtil.PASSWORD_PATTERN, message = "Weak password.")
    @NotBlank
    private String password;

    @Pattern(regexp = PasswordUtil.PASSWORD_PATTERN, message = "Weak password.")
    @NotBlank
    private String confirm;

}
