package lk.ijse.gdse69.javafx.Util;

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


}
