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
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSectionController extends MainDashBoard implements Initializable {

    public AnchorPane MainAnchorPane;
    public TextField searchSection;
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
        setNextSectionId();
        setSearchSectionId();

    }

    private void setSearchSectionId() {
        List<String> sectionIds = new ArrayList<>();

        try {
            List<Section> allSections = SectionRepo.getAllSections();
            for (Section section : allSections) {
                sectionIds.add(section.getSectionId()+" - "+section.getSectionName());
            }
            String[] possibleNames = sectionIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(searchSection, possibleNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setNextSectionId() {
        try {
            List<Section> allSections = SectionRepo.getAllSections();
            if (allSections.size() == 0){
                sectionId.setText("S001");
            }
            else {
                int lastSectionId = Integer.parseInt(allSections.get(allSections.size()-1).getSectionId().substring(1));
                lastSectionId++;
                if (lastSectionId < 10){
                    sectionId.setText("S00"+lastSectionId);
                }
                else if (lastSectionId < 100){
                    sectionId.setText("S0"+lastSectionId);
                }
                else {
                    sectionId.setText("S"+lastSectionId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sectionId.setEditable(false);

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

            if (!Util.checkInt(capacity.getText())){
                ShowAlert.showErrorNotify("Invalid Capacity. Please enter a valid capacity");
                return;
            }

            String sectionId = this.sectionId.getText();
            String sectionName = this.sectionName.getText();
            String location = this.location.getText();
            String capacity = this.capacity.getText();
            String securityLevel = securityLevelCombo.getSelectionModel().getSelectedItem();
            String status = statusCombo.getSelectionModel().getSelectedItem();

            Section section = new Section(sectionId, sectionName, location, Integer.parseInt(capacity), securityLevel, status);
            if (SectionRepo.save(section)) {
                ShowAlert.showSuccessNotify("Section Added Successfully");
                clearField();
                setTotalCount();
            } else {
                ShowAlert.showErrorNotify("Failed to add Section");
            }
        } else {
            ShowAlert.showErrorNotify("Please fill all the fields");
        }
    }

    private boolean checkEmpty() {
        return Util.checkEmptyFields(sectionId.getText(),sectionName.getText(),location.getText(),capacity.getText(),securityLevelCombo.getSelectionModel().getSelectedItem(),statusCombo.getSelectionModel().getSelectedItem());
    }

    private void clearField(){
        sectionId.clear();
        sectionName.clear();
        location.clear();
        capacity.clear();
        securityLevelCombo.getSelectionModel().clearSelection();
        statusCombo.getSelectionModel().clearSelection();
        setNextSectionId();
    }

    public void searchSectionField(ActionEvent actionEvent) throws IOException {
        String id = searchSection.getText().split(" - ")[0];
        SearchId.setVisitorId(id);
        createStage("/View/SectionProfile.fxml");
    }
}
