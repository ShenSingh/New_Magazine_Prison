package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AddInmateRecordController extends MainDashBoard{

    public JFXToggleButton editProfile;



    public void caseStatusField(ActionEvent actionEvent) {
    }

    public void inmateProfile(ActionEvent actionEvent) throws IOException {
        createStage("/View/InmateProfile.fxml");
    }

    public void showRecordBtn(ActionEvent actionEvent) {
    }

    public void showIncidentRecordBtn(ActionEvent actionEvent) {
    }

    public void editProfileTogal(ActionEvent actionEvent) {
    }
}
