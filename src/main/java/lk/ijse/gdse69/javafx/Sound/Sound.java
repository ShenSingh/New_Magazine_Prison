package lk.ijse.gdse69.javafx.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class Sound {

    public static final String oneClickSound01 = "/sounds/oneClickSound01.mp3";
    public static final String doubleClickSound01 = "/sounds/doubleClickSound01.mp3";
    public static final String notifySound01 = "/sounds/notifySound01.mp3";

    public void playNotifySound() {
        playSound(notifySound01);
        System.out.println("Sound played notify");
    }

    public void playOneClickSound() {
        System.out.println("Sound played one click");
        playSound(oneClickSound01);
    }

    public void playDoubleClickSound() {
        System.out.println("Sound played double click");
        playSound(doubleClickSound01);
    }

    private void playSound(String soundPath) {
        try {
            URL url = Sound.class.getResource(soundPath);
            if (url != null) {
                Media media = new Media(url.toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                System.out.println("Sound played - " + soundPath);
                mediaPlayer.play();
            } else {
                System.err.println("Sound resource not found: " + soundPath);
            }
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

}
