package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import lk.ijse.gdse69.javafx.Util.Util;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSectionController extends MainDashBoard implements Initializable {

    public AnchorPane MainAnchorPane;
    @FXML
    private TextField sectionId;
    @FXML
    private TextField sectionName;
    @FXML
    private TextField location;
    @FXML
    private TextField capacity;
    @FXML
    private ComboBox<String> securityLevelCombo;
    @FXML
    private ComboBox<String> statusCombo;

    @FXML
    private Text totalSectionCount;

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
        securityLevelCombo.getItems().addAll("Low", "Medium", "High");
        statusCombo.getItems().addAll("Active", "Inactive");

        setTotalCount();
        setToolTip();

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

    private void setTotalCount() {
        try {
            String count=(String.valueOf(SectionRepo.getAllSections().size()));
            totalSectionCount.setText(count+" Sections");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void canselBtn(ActionEvent actionEvent) {
        clearField();
    }


    public void submitBtn(ActionEvent actionEvent) throws SQLException {

        if (checkEmpty()) {

            if(checkValidSectionId()){}else {return;}

            String sectionId = this.sectionId.getText();
            String sectionName = this.sectionName.getText();
            String location = this.location.getText();
            String capacity = this.capacity.getText();
            String securityLevel = securityLevelCombo.getSelectionModel().getSelectedItem();
            String status = statusCombo.getSelectionModel().getSelectedItem();

            boolean isValid=checkSectionId(sectionId);

            if (isValid){
                Section section = new Section(sectionId, sectionName, location, Integer.parseInt(capacity), securityLevel, status);

                if (SectionRepo.save(section)) {
                    System.out.println("Section added successfully");
                    ShowAlert showAlert = new ShowAlert("Success", "Section Added", "Section added successfully", Alert.AlertType.INFORMATION);
                    clearField();
                } else {
                    System.out.println("Failed to add section");
                    ShowAlert showAlert = new ShowAlert("Error", "Failed to add section", "Failed to add section", Alert.AlertType.ERROR);
                    clearField();
                }
            }else{
                System.out.println("Section Id already exist");
                ShowAlert showAlert = new ShowAlert("Error", "Section Id already exist", "Section Id already exist", Alert.AlertType.WARNING);
                clearField();
            }


        } else {
            System.out.println("Empty fields found");
            ShowAlert showAlert = new ShowAlert("Error", "Empty Fields", "Please fill all the fields", Alert.AlertType.ERROR);
        }
    }

    private boolean checkValidSectionId() {
        String secId = this.sectionId.getText().trim();

        if (secId.matches("S\\d{3}")){
            return true;
        }
        ShowAlert showAlert = new ShowAlert("Error", "Invalid Section Id", "Invalid Section Id Ex : SXXX", Alert.AlertType.WARNING);
        return false;
    }

    private boolean checkSectionId(String sectionId) {

        List<Section> allSection = new ArrayList<>();

        try {
            allSection = SectionRepo.getAllSections();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(Section section:allSection){
            if(!section.getSectionId().equals(sectionId)){
                return true;
            }
        }
        ShowAlert showAlert = new ShowAlert("Error", "Section Id Already Exist", "Section Id Already Exist", Alert.AlertType.WARNING);
        return false;
    }

    private boolean checkEmpty() {

//        if (sectionId.getText().isEmpty() || sectionName.getText().isEmpty() || location.getText().isEmpty() || capacity.getText().isEmpty() || securityLevelCombo.getSelectionModel().isEmpty() || statusCombo.getSelectionModel().isEmpty()){
//
//            return false;
//        }
//        return true;

        return Util.checkEmptyFields(sectionId.getText(),sectionName.getText(),location.getText(),capacity.getText(),securityLevelCombo.getSelectionModel().getSelectedItem(),statusCombo.getSelectionModel().getSelectedItem());

    }

    private void clearField(){
        sectionId.clear();
        sectionName.clear();
        location.clear();
        capacity.clear();
        securityLevelCombo.getSelectionModel().clearSelection();
        statusCombo.getSelectionModel().clearSelection();
    }

}
