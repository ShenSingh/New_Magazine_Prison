package lk.ijse.gdse69.javafx.Controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class InmateProfileController extends  MainDashBoard{

    @FXML
    private AnchorPane iconsPane;
    @FXML
    private AnchorPane incidentRecordAnchor;

    @FXML
    private TextField inmateId;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private TextField NIC;
    @FXML
    private TextField gender;
    @FXML
    private TextField address;
    @FXML
    private TextField status;
    @FXML
    private TextField DOB;

   


    private boolean isIconNamesVisible1 = false;
    private boolean isIconNamesVisible2 = false;
    private TranslateTransition slideTransition1;
    private TranslateTransition slideTransition2;

    private boolean isEditingEnabled = false;

    @FXML
    public void initialize() {
        // Initialize translate transitions for iconsPane
        slideTransition1 = new TranslateTransition(Duration.seconds(0.3), iconsPane);
        slideTransition1.setToX(450); // Slide distance to hide names initially

        // Initialize translate transitions for incidentRecordAnchor
        slideTransition2 = new TranslateTransition(Duration.seconds(0.3), incidentRecordAnchor);
        slideTransition2.setToX(450); // Slide distance to hide names initially
        iconsPane.setVisible(false);
        incidentRecordAnchor.setVisible(false);
    }

    @FXML
    public void showRecordBtn(ActionEvent actionEvent) {
        incidentRecordAnchor.setVisible(false);
        iconsPane.setVisible(true);
        // Toggle visibility and animate iconsPane
        isIconNamesVisible1 = !isIconNamesVisible1;
        if (isIconNamesVisible1) {
            slideTransition1.setToX(450); // Slide to reveal names
        } else {
            slideTransition1.setToX(0); // Slide to hide names
        }
        slideTransition1.play();
    }

    @FXML
    public void showIncidentRecordBtn(ActionEvent actionEvent) {
        iconsPane.setVisible(false);
        incidentRecordAnchor.setVisible(true);
        // Toggle visibility and animate incidentRecordAnchor
        isIconNamesVisible2 = !isIconNamesVisible2;
        if (isIconNamesVisible2) {
            slideTransition2.setToX(500); // Slide to reveal names
        } else {
            slideTransition2.setToX(0); // Slide to hide names
        }
        slideTransition2.play();
    }

    public void editProfileTogal(ActionEvent actionEvent) {
        isEditingEnabled = !isEditingEnabled;

        inmateId.setEditable(isEditingEnabled);
        fName.setEditable(isEditingEnabled);
        lName.setEditable(isEditingEnabled);
        NIC.setEditable(isEditingEnabled);
        DOB.setEditable(isEditingEnabled);
        address.setEditable(isEditingEnabled);
        status.setEditable(isEditingEnabled);
        gender.setEditable(isEditingEnabled);
    }
}
