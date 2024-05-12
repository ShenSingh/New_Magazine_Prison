package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.InmateRecord;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Model.SetFirstInmateRecord;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import lk.ijse.gdse69.javafx.Repository.SetFirstInmateRecordRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;

    public void initialize() {

        setToolTip();
        System.out.println("Add Inmate Page initialized");

        // Initialize the ComboBox with options
        caseStatusComboBox.getItems().addAll("Low", "Medium", "High");
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

            if (checkValidateInmateId()){}else {return;}

            if (checkInmateId()){}else {return;}

            if(checkSectionId()){}else {return;}

            Inmate inmate = new Inmate(inmateId.getText(), inmateFName.getText(), inmateLName.getText(), inmateDOB.getValue(),inmateNIC.getText(), inmateGender.getText(), inmateAddress.getText(), inmateStatus.getText());
            InmateRecord inmateRecord = new InmateRecord( inmateId.getText(), inRecoSectionId.getText(), Date.valueOf(LocalDate.now()), Date.valueOf(inRecoReleseDate.getValue()),inRecoCrime.getText() ,caseStatusComboBox.getSelectionModel().getSelectedItem());

            SetFirstInmateRecord firstInmateRecord =new SetFirstInmateRecord(inmate,inmateRecord);

            if (SetFirstInmateRecordRepo.setFirstInmateRecord(firstInmateRecord)) {
                System.out.println("Inmate record added successfully");
                showAlert = new ShowAlert("Success", "Record Added", "Record added successfully", Alert.AlertType.INFORMATION);
                clearFields();
            }
            else {
                System.out.println("Inmate record not added successfully");
                showAlert = new ShowAlert("Error", "Record Not Added", "Record not added successfully", Alert.AlertType.INFORMATION);
                clearFields();
            }
        }
        else {
            System.out.println("Please fill all the fields");
            showAlert = new ShowAlert("Error", "Empty Fields", "Please fill all the fields", Alert.AlertType.INFORMATION);
            clearFields();
            // Show an alert to the user to fill all the fields
        }
    }

    private boolean checkValidateInmateId() {
        String id = this.inmateId.getText().trim();

        if (id.matches("I\\d{3}")){
            return true;
        }
        ShowAlert showAlert = new ShowAlert("Error", "Invalid Inmate Id", "Invalid Inmate Id Ex : IXXX", Alert.AlertType.ERROR);
        return false;
    }

    private boolean checkSectionId() {
        List<Section> sections = new ArrayList<>();

        try {
            sections = SectionRepo.getAllSections();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Section section : sections){
            if (section.getSectionId().equals(this.inRecoSectionId.getText())){
                return true;
            }
        }
        ShowAlert showAlert = new ShowAlert("Error", "Section Id Not Found", "Section Id Not Found", Alert.AlertType.ERROR);
        return false;
    }

    private boolean checkInmateId() {
        List<Inmate> inmates = new ArrayList<>();

        try {
            inmates = InmateRepo.getAllInmates();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (inmates.size() == 0){
            System.out.println("inmate count 0 ");
            return true;
        }

        for (Inmate inmate : inmates){
            if (!inmate.getInmateId().equals(this.inmateId.getText())){
                return true;
            }
        }
        ShowAlert showAlert = new ShowAlert("Error", "Inmate Id Already Exist", "Inmate Id Already Exist", Alert.AlertType.WARNING);
        return false;

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
