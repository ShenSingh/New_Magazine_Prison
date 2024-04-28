package lk.ijse.gdse69.javafx.Alert;

import javafx.scene.control.Alert;
import lk.ijse.gdse69.javafx.Sound.Sound;

public class ShowAlert {

    private String title;
    private String headerText;
    private String contentText;
    private Type alertType;



    public ShowAlert(String title, String headerText, String contentText, Type alertType) {
        this.title = title;
        this.headerText = headerText;
        this.contentText = contentText;
        this.alertType = alertType;


        show();
    }

    private void show() {
        Alert alert = new Alert(alertType == Type.INFORMATIONAL ? Alert.AlertType.INFORMATION :
                alertType == Type.WARNING ? Alert.AlertType.WARNING : Alert.AlertType.ERROR);
        new Sound().playOneClickSound();
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }


}
