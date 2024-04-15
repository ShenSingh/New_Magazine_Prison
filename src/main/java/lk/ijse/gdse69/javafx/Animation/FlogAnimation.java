package lk.ijse.gdse69.javafx.Animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FlogAnimation {
    private static String TEXT_CONTENT; // Text to be displayed
    private static final Duration ANIMATION_DURATION = Duration.seconds(2); // Total animation duration
    private static final Duration DELAY_BEFORE_RESTART = Duration.seconds(8); // Delay before restarting animation


    public FlogAnimation(Text text ,String content) {
        TEXT_CONTENT = content;
        animateText(text);
    }

    public static void animateText(Text text){

        int totalChars = TEXT_CONTENT.length();
        text.setText(""); // Clear text initially

        Timeline timeline = new Timeline();
        for (int i = 0; i <= totalChars; i++) {
            int currentIndex = i;
            KeyFrame keyFrame = new KeyFrame(
                    ANIMATION_DURATION.multiply(currentIndex).divide(totalChars),
                    e -> {
                        text.setText(TEXT_CONTENT.substring(0, currentIndex));
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        // Add a key frame to restart animation after a delay
        KeyFrame restartKeyFrame = new KeyFrame(
                ANIMATION_DURATION.add(DELAY_BEFORE_RESTART),
                e -> {
                    text.setText(""); // Reset text
                    timeline.playFromStart(); // Restart animation
                }
        );
        timeline.getKeyFrames().add(restartKeyFrame);

        // Set cycle count to indefinite to repeat animation continuously
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the animation
        timeline.play();
    }
}
