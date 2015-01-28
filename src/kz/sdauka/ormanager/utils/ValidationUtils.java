package kz.sdauka.ormanager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dauletkhan on 12.01.2015.
 */
public class ValidationUtils {

    public static boolean isEmailValid(String email) {
        boolean isEmailValid;
        Matcher emailMatcher;
        if (email == null || email.isEmpty()) {
            isEmailValid = false;
        } else {
            String emailRegex = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
            Pattern emailPattern = Pattern.compile(emailRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            emailMatcher = emailPattern.matcher(email);
            if (!(isEmailValid = emailMatcher.matches()) || email.length() > 128) {
                isEmailValid = false;
            }
        }
        return isEmailValid;
    }
}
