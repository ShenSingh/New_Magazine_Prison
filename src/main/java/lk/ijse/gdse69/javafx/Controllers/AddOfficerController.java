package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.Model.Officer;
import lk.ijse.gdse69.javafx.Repository.OfficerRepo;

import java.sql.SQLException;

public class AddOfficerController extends MainDashBoard {

    @FXML
    private TextField officerId;
    @FXML
    private TextField NIC;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;

    @FXML
    private TextField gender;

    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML
    private TextField number;

    @FXML
    private TextField salery;

    @FXML
    private DatePicker DOB;

    @FXML
    private TextField sectionId;


    @FXML
    private ComboBox<String> positionComboBox;

    ShowAlert showAlert;

    public void initialize() {
        System.out.println("Add Inmate Page initialized");

        // Initialize the ComboBox with options
        positionComboBox.getItems().addAll("Sergeant", "Lieutenant", "Captain", "Major", "Colonel", "General","Special Unit");
    }

    public void positionField(ActionEvent actionEvent){
        String selectedOption = positionComboBox.getSelectionModel().getSelectedItem();

        if (selectedOption != null) {
            System.out.println("Selected case status: " + selectedOption);

            // You can perform additional actions based on the selected case status
            // For example, update other UI elements or save the selected value
        }
    }


    public void submitBtn(ActionEvent actionEvent) throws SQLException {

        if (checkEmptyFields()){

            Officer officer = new Officer(officerId.getText(),fName.getText(),lName.getText(),java.sql.Date.valueOf(DOB.getValue()),NIC.getText(),gender.getText(),address.getText(),email.getText(),number.getText(),positionComboBox.getSelectionModel().getSelectedItem(),sectionId.getText(),Double.parseDouble(salery.getText()));

            officer.toString();

            if (OfficerRepo.save(officer)) {
                showAlert = new ShowAlert("Success", "Inmate Added", "Inmate added successfully", Type.INFORMATIONAL);
                clearFields();
            } else {
                showAlert = new ShowAlert("Error", "Inmate Not Added", "Inmate not added successfully", Type.ERROR);
            }

        }else {
            System.out.println("Empty Fields");

            clearFields();
        }
    }

    private void clearFields() {
        officerId.clear();
        NIC.clear();
        fName.clear();
        lName.clear();
        gender.clear();
        address.clear();
        DOB.getEditor().clear();
        email.clear();
        number.clear();
        salery.clear();
        positionComboBox.getSelectionModel().clearSelection();
        sectionId.clear();

    }

    public void canselBtn(ActionEvent actionEvent) {
    }

    public boolean checkEmptyFields() {
        if (officerId.getText().isEmpty() || NIC.getText().isEmpty() || fName.getText().isEmpty() ||
                lName.getText().isEmpty() || gender.getText().isEmpty() || address.getText().isEmpty() ||
                DOB.getValue() == null || email.getText().isEmpty() || number.getText().isEmpty() || salery.getText().isEmpty()){

            return false;

        }
        return true;
    }

}
