package lk.ijse.gdse69.javafx.Util;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

    public static byte[] readImage(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = fis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
    public static Image showImage(byte[] imageDate) {
        // Convert byte array to JavaFX Image
        Image image = new Image(new ByteArrayInputStream(imageDate));
        // Set the Image to the ImageView
        return image;

    }

    public static boolean checkInt(String text) {

        if(text.matches( "^\\d+$")){
            return true;
        }
        return false;
    }
}
