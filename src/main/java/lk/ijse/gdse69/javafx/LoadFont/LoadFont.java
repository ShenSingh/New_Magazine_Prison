package lk.ijse.gdse69.javafx.LoadFont;

import javafx.scene.text.Font;

import java.io.InputStream;

public class LoadFont {

    public Font checkFont(String font){

        Font font1 = null;

        switch (font){
            case "Comfortaa":

                font1=loadFont("/font/Comfortaa,Hammersmith_One,Kanit/Comfortaa/Comfortaa-Regular.ttf");
                break;
            case "Hammersmith One":
                font1=loadFont("/font/Comfortaa,Hammersmith_One,Kanit/Hammersmith_One/HammersmithOne-Regular.ttf");
                break;
            case "Kanit":
                font1=loadFont("/font/Comfortaa,Hammersmith_One,Kanit/Kanit/Kanit-Regular.ttf");
                break;
            case "KanitBold":
                font1=loadFont("/font/Comfortaa,Hammersmith_One,Kanit/Kanit/Kanit-Bold.ttf");
                break;
            default:
                System.out.println("Font not found");
        }

        return font1;
    }

    private Font loadFont(String fontPath) {
        try {
            // Load font file from resources
            InputStream fontStream = getClass().getResourceAsStream(fontPath);
            if (fontStream == null) {
                System.out.println("Font file not found: " + fontPath);

            }

            // Load the font with specified size
            Font font = Font.loadFont(fontStream,50); // Specify the font size
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle font loading error
        }
        return null;
    }
}
