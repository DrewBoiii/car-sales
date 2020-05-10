package example.drew.carsales.util;

import java.util.UUID;

public final class MailUtil {

    public static String getGeneratedCode() {
        return UUID.randomUUID().toString();
    }

}
