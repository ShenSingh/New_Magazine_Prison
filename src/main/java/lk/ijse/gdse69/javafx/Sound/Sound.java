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
    }

    public void playOneClickSound() {
        playSound(oneClickSound01);
    }

    public static void playDoubleClickSound() {
        playSound(doubleClickSound01);
    }

    private static void playSound(String soundPath) {
        try {
            URL url = Sound.class.getResource(soundPath);
            if (url != null) {
                Media media = new Media(url.toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            } else {
                System.err.println("Sound resource not found: " + soundPath);
            }
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        playDoubleClickSound();
    }

        //

}
