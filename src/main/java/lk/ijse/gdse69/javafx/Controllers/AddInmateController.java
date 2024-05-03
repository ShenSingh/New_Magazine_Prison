package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.InmateRecord;
import lk.ijse.gdse69.javafx.Model.SetFirstInmateRecord;
import lk.ijse.gdse69.javafx.Repository.SetFirstInmateRecordRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddInmateController extends MainDashBoard {

    public AnchorPane MainAnchorPane;
    @FXML
    private TextField inmateId;
    @FXML
    private TextField inmateNIC;
    @FXML
    private TextField inmateFName;
    @FXML
    private TextField inmateLName;
    @FXML
    private TextField inmateGender;
    @FXML
    private DatePicker inmateDOB;
    @FXML
    private TextField inmateAddress;
    @FXML
    private TextField inmateStatus;

    @FXML
    private TextField inRecoSectionId;
    @FXML
    private TextField inRecoCrime;
    @FXML
    private DatePicker inRecoReleseDate;

    @FXML
    private ComboBox<String> caseStatusComboBox;

    ShowAlert showAlert;

    public void initialize() {
        System.out.println("Add Inmate Page initialized");

        // Initialize the ComboBox with options
        caseStatusComboBox.getItems().addAll("Low", "Medium", "High");
    }

    @FXML
    public void caseStatusField(ActionEvent actionEvent) {
        // Get the selected item from the ComboBox
        String selectedOption = caseStatusComboBox.getSelectionModel().getSelectedItem();

        if (selectedOption != null) {
            System.out.println("Selected case status: " + selectedOption);

            // You can perform additional actions based on the selected case status
            // For example, update other UI elements or save the selected value
        }
    }


    @FXML
    public void submitBtn(ActionEvent actionEvent) throws IOException, SQLException {

        checkEmptyFields();

        if (checkEmptyFields()) {
            System.out.println("All fields are filled");
            // You can proceed with the form submission
            // For example, save the form data to the database



            Inmate inmate = new Inmate(inmateId.getText(), inmateFName.getText(), inmateLName.getText(), inmateDOB.getValue(),inmateNIC.getText(), inmateGender.getText(), inmateAddress.getText(), inmateStatus.getText());
            InmateRecord inmateRecord = new InmateRecord( inmateId.getText(), inRecoSectionId.getText(), Date.valueOf(LocalDate.now()), Date.valueOf(inRecoReleseDate.getValue()),inRecoCrime.getText() ,caseStatusComboBox.getSelectionModel().getSelectedItem());

            SetFirstInmateRecord firstInmateRecord =new SetFirstInmateRecord(inmate,inmateRecord);

            if (SetFirstInmateRecordRepo.setFirstInmateRecord(firstInmateRecord)) {
                System.out.println("Inmate record added successfully");
                showAlert = new ShowAlert("Success", "Record Added", "Record added successfully", Type.SUCCESS);
                clearFields();
            }
            else {
                System.out.println("Inmate record not added successfully");
                showAlert = new ShowAlert("Error", "Record Not Added", "Record not added successfully", Type.ERROR);
                clearFields();
            }
        }
        else {
            System.out.println("Please fill all the fields");
            showAlert = new ShowAlert("Error", "Empty Fields", "Please fill all the fields", Type.INFORMATIONAL);
            clearFields();
            // Show an alert to the user to fill all the fields
        }
    }

    private Boolean checkEmptyFields() {
        if (inmateId.getText().isEmpty() || inmateNIC.getText().isEmpty() || inmateFName.getText().isEmpty() || inmateLName.getText().isEmpty() || inmateGender.getText().isEmpty() || inmateDOB.getValue() == null || inmateAddress.getText().isEmpty() || inmateStatus.getText().isEmpty() || inRecoSectionId.getText().isEmpty() || inRecoCrime.getText().isEmpty() || inRecoReleseDate.getValue() == null || caseStatusComboBox.getSelectionModel().isEmpty()) {
            System.out.println("Please fill all the fields");

            return false;
        }
        else {
            System.out.println("All fields are filled");
            return true;
        }
    }

    private void clearFields() {
        inmateId.clear();
        inmateNIC.clear();
        inmateFName.clear();
        inmateLName.clear();
        inmateGender.clear();
        inmateDOB.getEditor().clear();
        inmateAddress.clear();
        inmateStatus.clear();
        inRecoSectionId.clear();
        inRecoCrime.clear();
        inRecoReleseDate.getEditor().clear();
        caseStatusComboBox.getSelectionModel().clearSelection();
    }




    public void canselBtn(ActionEvent actionEvent) {
        clearFields();
    }
}
