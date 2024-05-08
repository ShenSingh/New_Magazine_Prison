package lk.ijse.gdse69.javafx.Alert;

import javafx.scene.control.Alert;
import lk.ijse.gdse69.javafx.Sound.Sound;

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
        new Sound().playOneClickSound(); // Make sure to handle sound properly
    }
}
