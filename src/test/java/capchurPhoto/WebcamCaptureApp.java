package capchurPhoto;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class WebcamCaptureApp extends Application {

    private VideoCapture capture;
    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load OpenCV library

        capture = new VideoCapture(0); // Access the first webcam (index 0)
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640); // Set frame width
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480); // Set frame height

        imageView = new ImageView();
        Button captureButton = new Button("Capture Photo");

        captureButton.setOnAction(e -> capturePhoto());

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(imageView, captureButton);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Webcam Capture");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void capturePhoto() {
        Mat frame = new Mat();
        if (capture.isOpened()) {
            capture.read(frame); // Capture a frame from the webcam
            Image imageToShow = Utils.mat2Image(frame);
            imageView.setImage(imageToShow); // Display the captured image
        }
    }

    @Override
    public void stop() {
        if (capture != null) {
            capture.release(); // Release the webcam when the application is closed
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

