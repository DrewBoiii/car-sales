package example.drew.carsales.util;

import java.util.UUID;

public final class MailUtil {

    public static String getGeneratedCode() {
        return UUID.randomUUID().toString();
    }

    public static String getActivationCodeMessage(String username) {
        return String.format(
                "Hello, %s! \n" +
                        "Welcome to the News Blog!\n" +
                        "Please follow the link below to activate your account\n" +
                        "http://localhost:8080/activation/%s",
                username,
                getGeneratedCode()
        );
    }

    public static String getResetCodeMessage(String code) {
        return String.format(
                "Please follow the link below to confirm your identity\n" +
                        "http://localhost:8080/" +
                        "password/reset/confirm/%s",
                code
        );
    }

    public static String getNewTempPasswordMessage(String password) {
        return String.format(
                "New temporary password is %s\n" +
                        "It's recommended to change password asap.",
                password
        );
    }

}
