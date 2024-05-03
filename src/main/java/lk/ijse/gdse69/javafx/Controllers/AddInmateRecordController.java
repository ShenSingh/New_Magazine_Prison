package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.Model.InmateRecord;
import lk.ijse.gdse69.javafx.Repository.InmateRecordRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddInmateRecordController extends MainDashBoard{

    public JFXToggleButton editProfile;
    public AnchorPane MainAnchorPane;

    private boolean isIconNamesVisible1 = false;
    private boolean isIconNamesVisible2 = false;
    private TranslateTransition slideTransition1;
    private TranslateTransition slideTransition2;

    @FXML
    private AnchorPane iconsPane;
    @FXML
    private AnchorPane incidentRecordAnchor;

//    ///////////////////////

    @FXML
    private TextField sectionId;
    @FXML
    private TextField crime;
    @FXML
    private DatePicker releseDate;
    @FXML
    private ComboBox<String> caseStatusComboBox;

    @FXML
    private TextField searchInmate; // search Id //////////




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

        caseStatusComboBox.getItems().addAll("Pending", "Ongoing", "Closed");
    }

    public void caseStatusField(ActionEvent actionEvent) {
    }

    public void inmateProfile(ActionEvent actionEvent) throws IOException {
        createStage("/View/InmateProfile.fxml");
    }

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
    }

    public void inDeleteInmate(ActionEvent actionEvent) {
    }

    public void cancelBtn(ActionEvent actionEvent) {
    }

    public void submitBtn(ActionEvent actionEvent) throws SQLException {

        if (!searchInmate.getText().isEmpty()){
            if (checkEmptyField()){
                String inmateId = searchInmate.getText();
                String sectionId = this.sectionId.getText();
                String crime = this.crime.getText();
                Date entryDate = Date.valueOf(LocalDate.now());
                Date releseDate = Date.valueOf(this.releseDate.getValue());
                String caseStatus = caseStatusComboBox.getValue();

                InmateRecord inmateRecord = new InmateRecord(inmateId, sectionId, entryDate, releseDate, crime,caseStatus);


                System.out.println(inmateRecord.toString());

                if (InmateRecordRepo.save(inmateRecord)){
                    ShowAlert showAlert = new ShowAlert("Success", "Record Added", "Record added successfully", Type.SUCCESS);
                }else {
                    ShowAlert showAlert = new ShowAlert("Error", "Record Not Added", "Record not added successfully", Type.ERROR);
                }

            }else {
                ShowAlert showAlert = new ShowAlert("Error", "Empty Field", "Please fill all the fields", Type.ERROR);
            }

        }else{
            ShowAlert showAlert = new ShowAlert("Error", "Empty Field", "Please enter the inmate id", Type.ERROR);
        }
    }

    private boolean checkEmptyField() {
        if (sectionId.getText().isEmpty() || crime.getText().isEmpty() || releseDate.getValue() == null || caseStatusComboBox.getValue() == null){
            return false;
        }
        return true;
    }

    public void searchInmateField(ActionEvent actionEvent) {
    }
}
