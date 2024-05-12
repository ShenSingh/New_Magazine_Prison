package lk.ijse.gdse69.javafx.Alert;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.controlsfx.control.Notifications;

import java.net.URL;

public class ShowAlert {

    private final String title;
    private final String headerText;
    private final String contentText;
    private final Alert.AlertType alertType;

    public ShowAlert(String title, String headerText, String contentText, Alert.AlertType alertType) {
        this.title = title;
        this.headerText = headerText;
        this.contentText = contentText;
        this.alertType = alertType;
        show();
    }

    private void show() {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
        playSound();
    }

    public static void showErrorNotify(String contentText) {
        Image icon = new Image("file:///home/shen/Documents/myProject/NewManazinePrison/src/main/resources/images/icon/errorIcon.png");

        Notifications notifications = Notifications.create()
                .title("Error")
                .text(contentText)
                .graphic(new ImageView(icon)) // Set the image as the graphic
                .hideAfter(javafx.util.Duration.seconds(5))
                .position(javafx.geometry.Pos.BOTTOM_RIGHT);

        notifications.show();
        playSound();
    }
    public static void showSuccessNotify(String contentText) {
        Image icon = new Image("file:///home/shen/Documents/myProject/NewManazinePrison/src/main/resources/images/icon/successIcon.png");

        Notifications notifications = Notifications.create()
                .title("Success")
                .text(contentText)
                .graphic(new ImageView(icon)) // Set the image as the graphic
                .hideAfter(javafx.util.Duration.seconds(5))
                .position(javafx.geometry.Pos.BOTTOM_RIGHT);
        notifications.show();
        playSound();

    }
    private static void playSound() {
        String soundPath = "/sounds/notifySound01.mp3";
        try {
            URL url = ShowAlert.class.getResource(soundPath);
            if (url != null) {
                Media media = new Media(url.toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            }
        } catch (Exception e) {
        }
    }
}
