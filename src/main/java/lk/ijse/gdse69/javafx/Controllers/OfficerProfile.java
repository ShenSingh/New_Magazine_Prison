package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.Model.Officer;
import lk.ijse.gdse69.javafx.Repository.OfficerRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OfficerProfile extends MainDashBoard implements Initializable {

    @FXML
    private TextField officerId;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private TextField DOB;
    @FXML
    private TextField NIC;
    @FXML
    private TextField gender;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private TextField number;
    @FXML
    private TextField position;
    @FXML
    private TextField sectionId;
    @FXML
    private TextField salary;

    private boolean isEditingEnabled = false;

    @FXML
    private TextField searchField;


    private void initialize() {
        officerId.setEditable(false);
        fName.setEditable(false);
        lName.setEditable(false);
        DOB.setEditable(false);
        NIC.setEditable(false);
        gender.setEditable(false);
        address.setEditable(false);
        email.setEditable(false);
        number.setEditable(false);
        position.setEditable(false);
        sectionId.setEditable(false);
        salary.setEditable(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void deleteOfficer(ActionEvent actionEvent) {

        // Display confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Inmate");
        alert.setHeaderText("Delete Inmate");
        alert.setContentText("Are you sure you want to delete this inmate?");

        // Add "OK" and "Cancel" buttons
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);

        // Event handler for the "OK" button
        okButton.setOnAction(e -> {
            // Delete inmate (replace this with your actual deletion logic)
            goDeleteOfficer();

            // Close the alert
            alert.close();
        });

        // Event handler for the "Cancel" button
        cancelButton.setOnAction(e -> {
            // Close the alert without deleting the inmate
            alert.close();
        });

        alert.showAndWait();


    }

    private void goDeleteOfficer() {
        String officerId = this.officerId.getText();
        try {
            boolean isDeleted = OfficerRepo.delete(officerId);
            if (isDeleted) {
                ShowAlert alert1 = new ShowAlert("Success", "Officer Deleted", "Officer deleted successfully", Type.SUCCESS);
            } else {
                ShowAlert alert1 = new ShowAlert("Error", "Officer not deleted", "Officer not deleted successfully", Type.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editProfileTogal(ActionEvent actionEvent) {
        if (!isEditingEnabled) {
            fName.setEditable(true);
            lName.setEditable(true);
            DOB.setEditable(true);
            NIC.setEditable(true);
            gender.setEditable(true);
            address.setEditable(true);
            email.setEditable(true);
            number.setEditable(true);
            position.setEditable(true);
            sectionId.setEditable(true);
            salary.setEditable(true);
        }
    }

    public void seachOfficer(ActionEvent actionEvent) throws SQLException {
        String searchOfficerId = searchField.getText();
        Officer officer = OfficerRepo.search(searchOfficerId);

        if (officer != null) {
            this.officerId.setText(officer.getOfficerId());
            this.fName.setText(officer.getOfficerFirstName());
            this.lName.setText(officer.getOfficerLastName());
            this.DOB.setText(officer.getOfficerDOB().toString());
            this.NIC.setText(officer.getOfficerNIC());
            this.gender.setText(officer.getGender());
            this.address.setText(officer.getOfficerAddress());
            this.email.setText(officer.getOfficerEmail());
            this.number.setText(officer.getOfficerNumber());
            this.position.setText(officer.getPosition());
            this.sectionId.setText(officer.getSectionId());
            this.salary.setText(String.valueOf(officer.getSalary()));
        }else {
            System.out.println("Officer not found");
            ShowAlert alert = new ShowAlert("Error", "Officer not found", "Please enter a valid officer ID", Type.ERROR);
        }
    }

    public void saveBtn(ActionEvent actionEvent) {
        String officerId = this.officerId.getText();
        String fName = this.fName.getText();
        String lName = this.lName.getText();
        Date DOB = Date.valueOf(this.DOB.getText());
        String NIC = this.NIC.getText();
        String gender = this.gender.getText();
        String address = this.address.getText();
        String email = this.email.getText();
        String number = this.number.getText();
        String position = this.position.getText();
        String sectionId = this.sectionId.getText();
        String salary = this.salary.getText();

        Officer officer = new Officer(officerId, fName, lName, DOB, NIC,gender, address, email, number, position, sectionId, Double.parseDouble(salary));

        try {
            boolean isUpdated = OfficerRepo.update(officer);
            if (isUpdated) {
                ShowAlert alert = new ShowAlert("Success", "Officer Updated", "Officer updated successfully", Type.SUCCESS);
                setNewValues(officer);
            } else {
                ShowAlert alert = new ShowAlert("Error", "Officer not updated", "Officer not updated successfully", Type.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setNewValues(Officer officer) {
        this.officerId.setText(officer.getOfficerId());
        this.fName.setText(officer.getOfficerFirstName());
        this.lName.setText(officer.getOfficerLastName());
        this.DOB.setText(officer.getOfficerDOB().toString());
        this.NIC.setText(officer.getOfficerNIC());
        this.gender.setText(officer.getGender());
        this.address.setText(officer.getOfficerAddress());
        this.email.setText(officer.getOfficerEmail());
        this.number.setText(officer.getOfficerNumber());
        this.position.setText(officer.getPosition());
        this.sectionId.setText(officer.getSectionId());
        this.salary.setText(String.valueOf(officer.getSalary()));
    }

    public void cancelBtn(ActionEvent actionEvent) {
        clearField();
    }

    private void clearField() {
        officerId.clear();
        fName.clear();
        lName.clear();
        DOB.clear();
        NIC.clear();
        gender.clear();
        address.clear();
        email.clear();
        number.clear();
        position.clear();
        sectionId.clear();
        salary.clear();
    }
}
