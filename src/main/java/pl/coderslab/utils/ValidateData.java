package pl.coderslab.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateData {

    private static final Pattern USERNAME_REGEX =
            Pattern.compile("^\\S{5,255}$");
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,})$");
    private static final Pattern PASSWORD_REGEX =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{5,60}$");

    public static boolean validateUserName(String userName) {
        Matcher matcher = USERNAME_REGEX.matcher(userName);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        Matcher matcher = EMAIL_REGEX.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Matcher matcher = PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

}
