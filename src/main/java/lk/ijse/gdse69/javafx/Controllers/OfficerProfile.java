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
import lk.ijse.gdse69.javafx.Util.Util;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private ComboBox<String> sectionId;
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
        setSearchIds();
        this.officerId.setEditable(false);
    }

    private void setSearchIds() {
        List<String> offcerIds = new ArrayList<>();

        try {
            List<Officer> allOfficers = OfficerRepo.getAllOfficers();
            for (Officer officer : allOfficers) {
                offcerIds.add(officer.getOfficerId()+" - "+officer.getOfficerFirstName()+" "+officer.getOfficerLastName());
            }
            String[] possibleNames = offcerIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(searchField, possibleNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        try {
            for (Section section : SectionRepo.getAllSections()) {
                sectionId.getItems().add(section.getSectionId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOfficer(ActionEvent actionEvent) {

        if (searchField.getText().isEmpty()){
            ShowAlert.showErrorNotify("Please enter officer ID");
            return;
        }

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

        String id = searchField.getText().split(" - ")[0];

        if (id.isEmpty()){
            ShowAlert.showErrorNotify("Please enter officer ID");
            return;
        }
        try {
            boolean isDeleted = OfficerRepo.delete(id);
            if (isDeleted) {
                ShowAlert.showSuccessNotify("Officer deleted successfully");
                clearField();
            } else {
                ShowAlert.showErrorNotify("Failed to delete officer");
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

        if (searchField.getText().isEmpty()){
            ShowAlert.showErrorNotify("Please enter officer ID");
            return;
        }

        String searchOfficerId = searchField.getText().split(" - ")[0];;
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
            this.sectionId.getSelectionModel().select(officer.getSectionId());
            this.salary.setText(String.valueOf(officer.getSalary()));
        }else {
            ShowAlert.showErrorNotify("Officer not found");
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

        if (!Util.checkEmptyFields(officerId.getText(),fName.getText(),lName.getText(),DOB.getEditor().getText(),NIC.getText(),gender.getSelectionModel().getSelectedItem(),address.getText(),email.getText(),number.getText(),position.getSelectionModel().getSelectedItem(),sectionId.getSelectionModel().getSelectedItem(),salary.getText())){
            ShowAlert.showErrorNotify("Please fill all fields..");
            return;
        }

        if (checkInput()){}else {return;}

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
        String sectionId = this.sectionId.getSelectionModel().getSelectedItem();
        String salary = this.salary.getText();

        Officer officer = new Officer(officerId, fName, lName, DOB, NIC,gender, address, email, number, position, sectionId, Double.parseDouble(salary));

        try {
            boolean isUpdated = OfficerRepo.update(officer);
            if (isUpdated) {
                ShowAlert.showSuccessNotify("Officer updated successfully");
                setNewValues(officer);
            } else {
                ShowAlert.showErrorNotify("Failed to update officer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkInput() {

        if(Util.checkValidText(fName.getText()," \"^[A-z|\\\\s]{3,}$\"") && Util.checkValidText(lName.getText()," \"^[A-z|\\\\s]{3,}$\"")){
            ShowAlert.showErrorNotify("Invalid Name. Please enter a valid name");
            return false;
        }
        if (Util.checkValidText(NIC.getText(),"\"^[0-9]{9}[V|v]$\"")){
            ShowAlert.showErrorNotify("Invalid NIC. Please enter a valid NIC 123456789V");
            return false;
        }
        if (Util.checkValidText(address.getText(),"\"^[A-z|0-9|\\\\s|,|.|/]{3,}$\"")){
            ShowAlert.showErrorNotify("Invalid Address. Please enter a valid address");
            return false;
        }
        if (Util.checkValidText(email.getText(),"\"^[A-z|0-9|@|.]{3,}$\"")){
            ShowAlert.showErrorNotify("Invalid Email. Please enter a valid email");
            return false;
        }
        if (Util.checkValidText(number.getText(),"\"^[0-9]{10}$\"")){
            ShowAlert.showErrorNotify("Invalid Number. Please enter a valid number");
            return false;
        }
        if(Util.checkValidText(salary.getText(),"\"^[0-9]{3,}$\"")){
            ShowAlert.showErrorNotify("Invalid Salary. Please enter a valid salary");
            return false;
        }
        return true;

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
        this.sectionId.getSelectionModel().select(officer.getSectionId());
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
        sectionId.getSelectionModel().clearSelection();
        salary.clear();
        searchField.clear();
        clearSection();
    }
    private void clearSection() {
        OPsectionId.setText("");
        OPsectionName.setText("");
        OPlocation.setText("");
        OPcapacity.setText("");
        OPsecurityLevel.setText("");
        OPstatus.setText("");
    }
}
