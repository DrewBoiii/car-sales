package example.drew.carsales.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public final class PasswordUtil {

    public static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,128}$";
    public static final int PASSWORD_LENGTH = 10;

    private static final String LOWER_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%&*()_+-=[]|,./?><";

    public static String getGeneratedPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        SecureRandom random = new SecureRandom();

        List<String> boxOfChars = new ArrayList<>();
        boxOfChars.add(LOWER_CHARS);
        boxOfChars.add(UPPER_CHARS);
        boxOfChars.add(DIGITS);
        boxOfChars.add(SPECIAL_CHARS);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            String charString = boxOfChars.get(random.nextInt(boxOfChars.size()));
            int position = random.nextInt(charString.length());
            password.append(charString.charAt(position));
        }
        return new String(password);
    }

}
