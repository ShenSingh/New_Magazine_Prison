package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.Model.Officer;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.OfficerRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import lk.ijse.gdse69.javafx.Util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddOfficerController extends MainDashBoard {

    public AnchorPane MainAnchorPane;
    @FXML
    private TextField officerId;
    @FXML
    private TextField NIC;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;

    @FXML
    private ComboBox<String> gender;

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

    @FXML
    private Text totalOfficerCount;
    @FXML
    private Text maleOfficerCount;
    @FXML
    private Text feMaleOfficerCount;
    @FXML
    private Text specialUnitCount;

    ShowAlert showAlert;

    public void initialize() {
        System.out.println("Add Inmate Page initialized");

        setOfficerCount();

        // Initialize the ComboBox with options
        positionComboBox.getItems().addAll("Sergeant", "Lieutenant", "Captain", "Major", "Colonel", "General","Special Unit");
        gender.getItems().addAll(  "Male","Female");
    }

    private void setOfficerCount() {
        List<Officer> allOffisers = new ArrayList<>();

        try {
            allOffisers = OfficerRepo.getAllOfficers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (allOffisers != null) {
            totalOfficerCount.setText(String.valueOf(allOffisers.size()+" Officers"));

            long maleCount = allOffisers.stream().filter(officer -> officer.getGender().equals("Male")).count();
            maleOfficerCount.setText(String.valueOf(maleCount)+" Officers");

            long femaleCount = allOffisers.stream().filter(officer -> officer.getGender().equals("Female")).count();
            feMaleOfficerCount.setText(String.valueOf(femaleCount)+" Officers");

            specialUnitCount.setText(seUniCount(allOffisers)+" Officers");

        }
    }

    private String seUniCount(List<Officer> allOffisers) {
        long specialUnitCount = allOffisers.stream().filter(officer -> officer.getPosition().equals("Special Unit")).count();
        String specUniCount= String.valueOf(specialUnitCount);
        return specUniCount;
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
            if (checkValideOfficerid()){}else {return;}
            if (checkOfficerId()){}else {return;}
            if(checkSectionId()){}else {return;}
            if (checkValidName(this.fName.getText())){}else {return;}
            if (checkValidName(this.lName.getText())){}else {return;}

            if (Double.valueOf(this.salery.getText())  > 0){}else {
                showAlert = new ShowAlert("Error", "Officer Salary is Invalid", "Officer Salary is Invalid. Ex : xxxx.xx", Type.ERROR);
                return;
            }
            if (checkValidEmail()){}else {return;}

            Officer officer = new Officer(officerId.getText(),fName.getText(),lName.getText(),java.sql.Date.valueOf(DOB.getValue()),NIC.getText(),gender.getSelectionModel().getSelectedItem(),address.getText(),email.getText(),number.getText(),positionComboBox.getSelectionModel().getSelectedItem(),sectionId.getText(),Double.parseDouble(salery.getText()));



            if (OfficerRepo.save(officer)) {

                showAlert = new ShowAlert("Success", "Officer Added", "Officer added successfully", Type.INFORMATIONAL);
                clearFields();
            } else {
                showAlert = new ShowAlert("Error", "Officer Not Added", "Officer not added successfully", Type.ERROR);
            }

        }else {
            System.out.println("Empty Fields");

            clearFields();
        }
    }

    private boolean checkSectionId() {
        List<Section> sections = new ArrayList<>();

        try {
            sections = SectionRepo.getAllSections();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Section section : sections){
            if (section.getSectionId().equals(this.sectionId.getText())){
                return true;
            }
        }
        showAlert = new ShowAlert("Error", "Section Id Not Found", "Section Id Not Found", Type.ERROR);
        return false;
    }

    private boolean checkValidName(String text) {

        if (text.matches( "^[A-Za-z\\s'-]+$")){
            return true;
        }
        showAlert = new ShowAlert("Error", "Inmate Name Invalid", "Invalid Inmate first Name or Last Name", Type.ERROR);
        return false;
    }

    private boolean checkValidEmail() {
        String email = this.email.getText().trim();

        if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")){
            return true;
        }
        ShowAlert showAlert = new ShowAlert("Error", "Invalid Officer Email", "Invalid Officer Email Ex : xxxxx@gmail.com", Type.ERROR);
        return false;
    }

    private boolean checkValideOfficerid() {
        String id = this.officerId.getText().trim();

        if (id.matches("O\\d{3}")){
            return true;
        }
        ShowAlert showAlert = new ShowAlert("Error", "Invalid Officer Id", "Invalid Officer Id Ex : OXXX", Type.ERROR);
        return false;
    }

    private boolean checkOfficerId() {
        List<Officer> officers = new ArrayList<>();

        try {
            officers = OfficerRepo.getAllOfficers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Officer officer : officers){
            if (officer.getOfficerId().equals(this.officerId.getText())){
                ShowAlert showAlert = new ShowAlert("Error", "Officer Id Already Exist", "Officer Id Already Exist", Type.ERROR);
                return false;
            }
        }
        return true;
    }

    private void clearFields() {
        officerId.clear();
        NIC.clear();
        fName.clear();
        lName.clear();
        gender.getSelectionModel().clearSelection();
        address.clear();
        DOB.getEditor().clear();
        email.clear();
        number.clear();
        salery.clear();
        positionComboBox.getSelectionModel().clearSelection();
        sectionId.clear();

    }

    public void canselBtn(ActionEvent actionEvent) {
        clearFields();
    }

    public boolean checkEmptyFields() {
//        if (officerId.getText().isEmpty() || NIC.getText().isEmpty() || fName.getText().isEmpty() ||
//                lName.getText().isEmpty() || gender.getSelectionModel().getSelectedItem().isEmpty() || address.getText().isEmpty() ||
//                DOB.getValue() == null || email.getText().isEmpty() || number.getText().isEmpty() || salery.getText().isEmpty() || positionComboBox.getSelectionModel().getSelectedItem().isEmpty() || sectionId.getText().isEmpty()) {
//
//            return false;
//
//        }
//        return true;

        return Util.checkEmptyFields(officerId.getText(), NIC.getText(), fName.getText(), lName.getText(),
                gender.getSelectionModel().getSelectedItem(), address.getText(), email.getText(), number.getText(), salery.getText(),DOB.getValue().toString(),positionComboBox.getSelectionModel().getSelectedItem(),sectionId.getText());
    }

}
