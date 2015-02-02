package kz.sdauka.ormanager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dauletkhan on 12.01.2015.
 */
public class ValidationUtils {

    public static boolean isAdsExtension(String path) {
        return !(path == null || path.isEmpty());
    }

    public static boolean isExe(String path) {
        return !(path == null || path.isEmpty());

    }

    public static boolean isImg(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        } else {
            String file = path.substring(path.lastIndexOf("\\"));
            String extension = file.substring(file.indexOf("."));
            return extension.equals(".jpg") || extension.equals(".png");
        }
    }

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
