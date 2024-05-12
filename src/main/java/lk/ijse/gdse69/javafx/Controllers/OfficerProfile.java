package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Officer;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.OfficerRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OfficerProfile extends MainDashBoard implements Initializable {

    @FXML
    private TextField officerId;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private DatePicker DOB;
    @FXML
    private TextField NIC;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private TextField number;
    @FXML
    private ComboBox<String> position;
    @FXML
    private TextField sectionId;
    @FXML
    private TextField salary;

    private boolean isEditingEnabled = false;

    @FXML
    private TextField searchField;


    @FXML
    private Text OPsectionId;
    @FXML
    private Text OPsectionName;
    @FXML
    private Text OPlocation;
    @FXML
    private Text OPcapacity;
    @FXML
    private Text OPsecurityLevel;
    @FXML
    private Text OPstatus;


    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setToolTip();
        setValuesComboBoxes();
    }

    private void setToolTip() {
        Tooltip.install(inmateBtn, new Tooltip("Inmate Management"));
        Tooltip.install(officerBtn, new Tooltip("Officer Management"));
        Tooltip.install(dashBoardBtn, new Tooltip("DashBoard"));
        Tooltip.install(settingBtn, new Tooltip("Setting"));
        Tooltip.install(manyBtn, new Tooltip("Financial Management"));
        Tooltip.install(sectionBtn, new Tooltip("Section Management"));
        Tooltip.install(visitorBtn, new Tooltip("Visitor Management"));
    }

    private void setValuesComboBoxes() {
        gender.getItems().addAll("Male","Female");
        position.getItems().addAll("Sergeant", "Lieutenant", "Captain", "Major", "Colonel", "General","Special Unit");
    }

    public void deleteOfficer(ActionEvent actionEvent) {

        // Display confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Officer");
        alert.setHeaderText("Delete officer");
        alert.setContentText("Are you sure you want to delete this officer?");

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);

        okButton.setOnAction(e -> {
            goDeleteOfficer();

            alert.close();
        });
        cancelButton.setOnAction(e -> {
            alert.close();
        });

        alert.showAndWait();
    }

    private void goDeleteOfficer() {
        String officerId = this.officerId.getText();
        try {
            boolean isDeleted = OfficerRepo.delete(officerId);
            if (isDeleted) {
                ShowAlert alert1 = new ShowAlert("Success", "Officer Deleted", "Officer deleted successfully", Alert.AlertType.INFORMATION);
            } else {
                ShowAlert alert1 = new ShowAlert("Error", "Officer not deleted", "Officer not deleted successfully", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearField();
    }

    public void editProfileTogal(ActionEvent actionEvent) {

        isEditingEnabled = !isEditingEnabled;

            fName.setEditable(isEditingEnabled);
            lName.setEditable(isEditingEnabled);
            DOB.setEditable(isEditingEnabled);
            NIC.setEditable(isEditingEnabled);
            address.setEditable(isEditingEnabled);
            email.setEditable(isEditingEnabled);
            number.setEditable(isEditingEnabled);
            sectionId.setEditable(isEditingEnabled);
            salary.setEditable(isEditingEnabled);

        for (String item : gender.getItems()){
            gender.setDisable(!isEditingEnabled);
        }
        for (String item : position.getItems()){
            position.setDisable(!isEditingEnabled);
        }

    }

    public void seachOfficer(ActionEvent actionEvent) throws SQLException {
        String searchOfficerId = searchField.getText();
        Officer officer = OfficerRepo.search(searchOfficerId);

        if (officer != null) {

            setSectionValues(officer.getSectionId());
            this.officerId.setText(officer.getOfficerId());
            this.fName.setText(officer.getOfficerFirstName());
            this.lName.setText(officer.getOfficerLastName());
            this.DOB.setValue(LocalDate.parse(officer.getOfficerDOB().toString()));
            this.NIC.setText(officer.getOfficerNIC());
            this.gender.getSelectionModel().select(officer.getGender());
            this.address.setText(officer.getOfficerAddress());
            this.email.setText(officer.getOfficerEmail());
            this.number.setText(officer.getOfficerNumber());
            this.position.getSelectionModel().select(officer.getPosition());
            this.sectionId.setText(officer.getSectionId());
            this.salary.setText(String.valueOf(officer.getSalary()));
        }else {
            System.out.println("Officer not found");
            ShowAlert alert = new ShowAlert("Error", "Officer not found", "Please enter a valid officer ID", Alert.AlertType.ERROR);
        }
    }

    private void setSectionValues(String sectionId){
        Section section = null;
        try {
           section = SectionRepo.search(sectionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (section != null){
            OPsectionId.setText(section.getSectionId());
            OPsectionName.setText(section.getSectionName());
            OPlocation.setText(section.getLocation());
            OPcapacity.setText(String.valueOf(section.getCapacity()));
            OPsecurityLevel.setText(section.getSecurityLevel());
            OPstatus.setText(section.getStatus());
        }
    }
    public void saveBtn(ActionEvent actionEvent) {


        String officerId = this.officerId.getText();
        String fName = this.fName.getText();
        String lName = this.lName.getText();
        Date DOB = Date.valueOf(this.DOB.getValue());
        String NIC = this.NIC.getText();
        String gender = this.gender.getSelectionModel().getSelectedItem();
        String address = this.address.getText();
        String email = this.email.getText();
        String number = this.number.getText();
        String position = this.position.getSelectionModel().getSelectedItem();
        String sectionId = this.sectionId.getText();
        String salary = this.salary.getText();

        Officer officer = new Officer(officerId, fName, lName, DOB, NIC,gender, address, email, number, position, sectionId, Double.parseDouble(salary));

        try {
            boolean isUpdated = OfficerRepo.update(officer);
            if (isUpdated) {
                ShowAlert alert = new ShowAlert("Success", "Officer Updated", "Officer updated successfully", Alert.AlertType.INFORMATION);
                setNewValues(officer);
            } else {
                ShowAlert alert = new ShowAlert("Error", "Officer not updated", "Officer not updated successfully", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setNewValues(Officer officer) {
        this.officerId.setText(officer.getOfficerId());
        this.fName.setText(officer.getOfficerFirstName());
        this.lName.setText(officer.getOfficerLastName());
        this.DOB.setValue(LocalDate.parse(officer.getOfficerDOB().toString()));
        this.NIC.setText(officer.getOfficerNIC());
        this.gender.getSelectionModel().select(officer.getGender());
        this.address.setText(officer.getOfficerAddress());
        this.email.setText(officer.getOfficerEmail());
        this.number.setText(officer.getOfficerNumber());
        this.position.getSelectionModel().select(officer.getPosition());
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
        DOB.getEditor().clear();
        NIC.clear();
        gender.getSelectionModel().clearSelection();
        address.clear();
        email.clear();
        number.clear();
        position.getSelectionModel().clearSelection();
        sectionId.clear();
        salary.clear();
    }


}
