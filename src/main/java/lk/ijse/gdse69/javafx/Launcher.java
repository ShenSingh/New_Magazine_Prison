package lk.ijse.gdse69.javafx;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;

public class Launcher extends Application {
    static Stage signstage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        signstage = stage;

       StackPane root = setStartVideo();

        Scene scene = new Scene(root, 853, 480); // Create a Scene and set the StackPane as the root

        signstage.setTitle("New Magazine Prison-Sign In");
        signstage.setResizable(false);
        signstage.initStyle(StageStyle.UNDECORATED);
        signstage.setScene(scene);
        try {
            // Load FXML file
            Parent rootNode = FXMLLoader.load(getClass().getResource("/View/SignInPage.fxml"));
            Scene signInScene = new Scene(rootNode);
            int displayDurationMillis = 5000;// 1s
            PauseTransition delay = new PauseTransition(Duration.millis(displayDurationMillis));
            delay.setOnFinished(event -> signstage.setScene(signInScene));
            delay.play();
        } catch (Exception e) {
            e.printStackTrace(); // Print any exception to console
            // Handle the exception as necessary
        }
        // Show the Stage
        signstage.show();
    }

    private StackPane setStartVideo() {
        // Load the video file
        String videoPath = "/home/shen/Documents/myProject/NewManazinePrison/src/main/resources/Videos/Prison.mp4";
        Media media = new Media(new File(videoPath).toURI().toString());
        // Create a MediaPlayer
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        // Create a MediaView to display the video
        MediaView mediaView = new MediaView(mediaPlayer);
        // Set autoPlay to true
        mediaPlayer.setAutoPlay(true);
        // Create a StackPane and add the MediaView to it
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);

        return root;
    }

    public static Stage getStage() {

        return signstage;
    }
}
