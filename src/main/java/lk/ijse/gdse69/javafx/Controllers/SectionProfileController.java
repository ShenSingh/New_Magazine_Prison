package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;

import java.sql.SQLException;

public class SectionProfileController extends MainDashBoard{

    @FXML
    private TextField shearchSectionField;

    @FXML
    private TextField sectionId;
    @FXML
    private TextField secName;
    @FXML
    private TextField secLocation;
    @FXML
    private TextField capacity;
    @FXML
    private TextField securityLevel;

    @FXML
    private TextField status;

    public void searchSection(ActionEvent actionEvent) throws SQLException {
        String secId = shearchSectionField.getText();
        System.out.println(" section id -- "+secId);

        Section section = SectionRepo.search(secId);

        System.out.println(section.getSectionName());


        if (!(section ==null)){
            sectionId.setText(section.getSectionId());
            secName.setText(section.getSectionName());
            secLocation.setText(section.getLocation());
            capacity.setText(String.valueOf(section.getCapacity()));
            securityLevel.setText(section.getSecurityLevel());
            status.setText(section.getStatus());

        }else {
            ShowAlert alert = new ShowAlert("Error","Not Found","Please Enter Valid Id", Type.ERROR);
        }

    }

    public void secProfileBtn(ActionEvent actionEvent) {
    }

    public void deleteSection(ActionEvent actionEvent) {
    }

    public void showOfficers(ActionEvent actionEvent) {
    }

    public void showPrograms(ActionEvent actionEvent) {
    }

    public void editProfileTogal(ActionEvent actionEvent) {
    }

    public void saveBtn(ActionEvent actionEvent) throws SQLException {

        if (checkEmptyField()){
            String secId = sectionId.getText();
            String secName = this.secName.getText();
            String secLocation = this.secLocation.getText();
            String capacity = this.capacity.getText();
            String securityLevel = this.securityLevel.getText();
            String status = this.status.getText();

            Section section = new Section(secId, secName, secLocation, Integer.parseInt(capacity), securityLevel, status);

            System.out.println(section);

            if (SectionRepo.update(section)) {
                System.out.println("Section updated successfully");
                ShowAlert showAlert = new ShowAlert("Success", "Section Updated", "Section updated successfully", Type.SUCCESS);
            } else {
                System.out.println("Failed to update section");
                ShowAlert showAlert = new ShowAlert("Error", "Failed to update section", "Failed to update section", Type.ERROR);
            }

        } else {
            System.out.println("Empty fields found");
            ShowAlert showAlert = new ShowAlert("Error", "Empty Fields", "Please fill all the fields", Type.ERROR);
        }
    }

    private boolean checkEmptyField() {

        if (sectionId.getText().isEmpty() || secName.getText().isEmpty() || secLocation.getText().isEmpty() || capacity.getText().isEmpty() || securityLevel.getText().isEmpty() || status.getText().isEmpty()){
            return false;
        }
        return true;
    }

    public void cancelBtn(ActionEvent actionEvent) {
    }
}
