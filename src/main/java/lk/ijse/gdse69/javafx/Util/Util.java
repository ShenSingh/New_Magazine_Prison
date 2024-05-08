package lk.ijse.gdse69.javafx.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static boolean checkEmptyFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkValidText(String field , String regex){
        String reg = regex;

        if(field.matches(reg)){
            return true;
        }
        return false;

    }

    public static boolean validateTime(String time) {
        String regex = "^(?:[01]\\d|2[0-3]):[0-5]\\d$"; // Matches hours (00-23) followed by a colon and minutes (00-59)
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }


}
