package example.drew.carsales.util;

import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordUtil {

    public static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,128}$";

    public static String getGeneratedPassword() {
        byte[] bytes = new byte[10];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

}
