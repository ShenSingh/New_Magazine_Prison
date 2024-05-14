package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.Officer;
import lk.ijse.gdse69.javafx.Model.Program;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.*;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class SectionProfileController extends MainDashBoard implements Initializable {

    public AnchorPane MainAnchorPane;
    public Text selectSecName;


    public AnchorPane inmateAnchor;
    public AnchorPane programAnchor;
    public AnchorPane officerAnchor;


    public Text secCapacityText;
    public Text secSecuLevelText;
    public Text secStatusText;
    public Text secLocationText;
    public Text secNameText;
    public Text secIdText;

    public AnchorPane line1;
    public AnchorPane line2;
    public AnchorPane line3;
    public AnchorPane line4;
    public AnchorPane line5;
    public AnchorPane line6;

    public Text mainPD;
    public Text secondPD;
    public JFXButton saveBtn;
    public JFXButton cancelBtn;

    public TableColumn<Officer, String> toAddress;
    public TableColumn<Officer, String> toFName;
    public TableColumn<Officer, String> toLName;
    public TableColumn<Officer, String> toId;
    public TableColumn<Officer, String> toDOB;
    public TableColumn<Officer, String > toNIC;
    public TableColumn<Officer, String> toGender;
    public TableColumn<Officer, String> toEmail;
    public TableColumn<Officer,String> toNumber;
    public TableColumn<Officer, String> toPosition;
    public TableColumn<Officer, String> toSalary;

    /////////////////////////////
    public TableColumn<Inmate, String > tiId;
    public TableColumn<Inmate, String> tiFName;
    public TableColumn<Inmate, String> tiLName;
    public TableColumn<Inmate, String> tiDOB;
    public TableColumn<Inmate, String> tiNIC;
    public TableColumn<Inmate, String> tiGender;
    public TableColumn<Inmate, String> tiAddress;
    public TableColumn<Inmate, String> tiStatus;

    public TableColumn<Program, String> tpProgramId;
    public TableColumn<Program, String> tpName;
    public TableColumn<Program, String> tpSecId;
    public TableColumn<Program, String> tpDate;
    public TableColumn<Program, String> tpTime;
    public TableColumn<Program, String> tpDescription;


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
    private ComboBox<String> securityLevel;
    @FXML
    private ComboBox<String> status;

    private TranslateTransition sideTransition_1;
    private TranslateTransition sideTransition_2;
    private TranslateTransition sideTransition_3;

    private boolean isOfficerVisible = false;
    private boolean isProgramVisible = false;
    private boolean isInmateVisible = false;

    private boolean isEditableEnable = false;

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
        setTransition();
        setComboBoxValues();
        setToolTip();
        setSearchIds();
        sectionId.setEditable(false);
    }

    private void setSearchIds() {
        List<String> sectionIds = new ArrayList<>();

        try {
            List<Section> allSections = SectionRepo.getAllSections();
            for (Section section : allSections) {
                sectionIds.add(section.getSectionId()+" - "+section.getSectionName());
            }
            String[] possibleNames = sectionIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(shearchSectionField, possibleNames);
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

    private void setComboBoxValues() {
        securityLevel.getItems().addAll("High", "Medium", "Low");
        status.getItems().addAll("Active", "Inactive");
    }

    private void setTransition() {
        sideTransition_1 = new TranslateTransition(Duration.seconds(0.3), officerAnchor);
        sideTransition_1.setFromX(450);

        sideTransition_2 = new TranslateTransition(Duration.seconds(0.3), programAnchor);
        sideTransition_2.setFromX(450);

        sideTransition_3 = new TranslateTransition(Duration.seconds(0.3), inmateAnchor);
        sideTransition_3.setFromX(450);

        officerAnchor.setVisible(false);
        programAnchor.setVisible(false);
        inmateAnchor.setVisible(false);
    }

    public void showOfficersBtn(ActionEvent actionEvent) {
        officerAnchor.setVisible(true);
        programAnchor.setVisible(false);
        inmateAnchor.setVisible(false);

        isOfficerVisible = !isOfficerVisible;
        if(isOfficerVisible){
            sideTransition_1.setToX(730);
            centerContentHideShow(isOfficerVisible);
        }else{
            sideTransition_1.setToX(0);
            centerContentHideShow(isOfficerVisible);
        }
        sideTransition_1.play();
    }

    public void showProgramsBtn(ActionEvent actionEvent) {
        programAnchor.setVisible(true);
        officerAnchor.setVisible(false);
        inmateAnchor.setVisible(false);

        isProgramVisible = !isProgramVisible;
        if(isProgramVisible){
            sideTransition_2.setToX(730);
            centerContentHideShow(isProgramVisible);
        }else{
            sideTransition_2.setToX(0);
            centerContentHideShow(isProgramVisible);
        }
        sideTransition_2.play();
    }
    public void showInmatesBtn(ActionEvent actionEvent) {
        inmateAnchor.setVisible(true);
        officerAnchor.setVisible(false);
        programAnchor.setVisible(false);

        isInmateVisible = !isInmateVisible;
        if(isInmateVisible){
            sideTransition_3.setToX(730);
            centerContentHideShow(isInmateVisible);
        }else{
            sideTransition_3.setToX(0);
            centerContentHideShow(isInmateVisible);
        }
        sideTransition_3.play();
    }

    private void centerContentHideShow(boolean isVisible) {
        sectionId.setVisible(isVisible);
        secName.setVisible(isVisible);
        secLocation.setVisible(isVisible);
        capacity.setVisible(isVisible);
        securityLevel.setVisible(isVisible);
        status.setVisible(isVisible);

        secIdText.setVisible(isVisible);
        secNameText.setVisible(isVisible);
        secLocationText.setVisible(isVisible);
        secCapacityText.setVisible(isVisible);
        secSecuLevelText.setVisible(isVisible);
        secStatusText.setVisible(isVisible);

        line1.setVisible(isVisible);
        line2.setVisible(isVisible);
        line3.setVisible(isVisible);
        line4.setVisible(isVisible);
        line5.setVisible(isVisible);
        line6.setVisible(isVisible);

        mainPD.setVisible(isVisible);
        secondPD.setVisible(isVisible);

        saveBtn.setVisible(isVisible);
        cancelBtn.setVisible(isVisible);
    }

    public void searchSection(ActionEvent actionEvent) throws SQLException {
        String secId = shearchSectionField.getText().split(" - ")[0];

        if (secId.isEmpty()){
            ShowAlert.showErrorNotify("Empty Field. Please enter section id");
            return;
        }

        Section section = SectionRepo.search(secId);

        if (section != null){

            setTableValues(secId);
            sectionId.setText(section.getSectionId());
            secName.setText(section.getSectionName());
            secLocation.setText(section.getLocation());
            capacity.setText(String.valueOf(section.getCapacity()));
            securityLevel.getSelectionModel().select(section.getSecurityLevel());
            status.getSelectionModel().select(section.getStatus());
        }else {
            ShowAlert.showErrorNotify("Section not found");
        }
    }

    private void setTableValues(String secId) throws SQLException {

        List<Officer> officers = OfficerRepo.getOfficersBySection(secId);
        setOfficerTableValues(officers);

        List<String> inmateid = InmateRecordRepo.getInmatesIdBySection(secId);

        for (int i = 0; i < inmateid.size(); i++) {
            System.out.println(inmateid.get(i));
        }

        Set<String> inmateIds = new HashSet<>(inmateid);

        List<Inmate> inmatesBySec = new ArrayList<>();

        for (String id : inmateIds) {
            Inmate inmate = InmateRepo.search(id);
            inmatesBySec.add(inmate);
        }
        setInmateTableValues(inmatesBySec);




        List<Program> programs = ProgramRepo.getProgramBySection(secId);

        setProgramTableValues(programs);

    }

    private void setProgramTableValues(List<Program> programs) {
        tpProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        tpName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        tpSecId.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
        tpDate.setCellValueFactory(new PropertyValueFactory<>("programDate"));
        tpTime.setCellValueFactory(new PropertyValueFactory<>("programTime"));
        tpDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        tpProgramId.getTableView().setItems(FXCollections.observableArrayList(programs));
    }

    private void setInmateTableValues(List<Inmate> inmatesBySec) {
        tiId.setCellValueFactory(new PropertyValueFactory<>("inmateId"));
        tiFName.setCellValueFactory(new PropertyValueFactory<>("inmateFirstName"));
        tiLName.setCellValueFactory(new PropertyValueFactory<>("inmateLastName"));
        tiDOB.setCellValueFactory(new PropertyValueFactory<>("inmateDOB"));
        tiNIC.setCellValueFactory(new PropertyValueFactory<>("inmateNIC"));
        tiGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tiAddress.setCellValueFactory(new PropertyValueFactory<>("inmateAddress"));
        tiStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tiAddress.getTableView().setItems(FXCollections.observableArrayList(inmatesBySec));
    }

    private void setOfficerTableValues(List<Officer> officers) {
        toId.setCellValueFactory(new PropertyValueFactory<>("officerId"));
        toFName.setCellValueFactory(new PropertyValueFactory<>("officerFirstName"));
        toLName.setCellValueFactory(new PropertyValueFactory<>("officerLastName"));
        toDOB.setCellValueFactory(new PropertyValueFactory<>("officerDOB"));
        toNIC.setCellValueFactory(new PropertyValueFactory<>("officerNIC"));
        toGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        toAddress.setCellValueFactory(new PropertyValueFactory<>("officerAddress"));
        toEmail.setCellValueFactory(new PropertyValueFactory<>("officerEmail"));
        toNumber.setCellValueFactory(new PropertyValueFactory<>("officerNumber"));
        toPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        toSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        toAddress.getTableView().setItems(FXCollections.observableArrayList(officers));
    }

    public void secProfileBtn(ActionEvent actionEvent) throws IOException {
        createStage("/View/SectionProfile.fxml");
    }

    public void deleteSection(ActionEvent actionEvent) throws SQLException {

        if (shearchSectionField.getText().isEmpty()){
            ShowAlert.showErrorNotify("Please enter Section ID");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Imformation");
        alert.setHeaderText("Delete Section");
        alert.setContentText("Are you sure you want to delete this Section?");

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);

        okButton.setOnAction(e -> {
            try {
                goDeleteSecction();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            alert.close();
        });
        cancelButton.setOnAction(e -> {
            alert.close();
        });

        alert.showAndWait();

    }

    private void goDeleteSecction() throws SQLException {
        if (!shearchSectionField.getText().isEmpty()){
            String sectionId = shearchSectionField.getText().split(" - ")[0];

            if (SectionRepo.delete(sectionId)){
                clearFields();
                ShowAlert.showSuccessNotify("Section deleted successfully");
            }else {
                ShowAlert.showErrorNotify("Failed to delete section");
            }
        }else{
            ShowAlert.showErrorNotify("Empty field. Please enter section id");
        }
    }

    public void editProfileTogal(ActionEvent actionEvent) {
        isEditableEnable = !isEditableEnable;

        secName.setEditable(isEditableEnable);
        secLocation.setEditable(isEditableEnable);
        capacity.setEditable(isEditableEnable);
        securityLevel.setDisable(!isEditableEnable);
        status.setDisable(!isEditableEnable);
    }

    public void saveBtn(ActionEvent actionEvent) throws SQLException {

        if (checkEmptyField()){
            String secId = sectionId.getText();
            String secName = this.secName.getText();
            String secLocation = this.secLocation.getText();
            String capacity = this.capacity.getText();
            String securityLevel = this.securityLevel.getSelectionModel().getSelectedItem();
            String status = this.status.getSelectionModel().getSelectedItem();

            Section section = new Section(secId, secName, secLocation, Integer.parseInt(capacity), securityLevel, status);


            if (SectionRepo.update(section)) {
                ShowAlert.showSuccessNotify("Section updated successfully");
            } else {
                ShowAlert.showErrorNotify("Failed to update section");
            }

        } else {
            ShowAlert.showErrorNotify("Empty field. Please fill all fields");
        }
    }

    private boolean checkEmptyField() {

        if (sectionId.getText().isEmpty() || secName.getText().isEmpty() || secLocation.getText().isEmpty() || capacity.getText().isEmpty() || securityLevel.getSelectionModel().getSelectedItem() == null || status.getSelectionModel().getSelectedItem() == null){
            return false;
        }
        return true;
    }

    public void cancelBtn(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        sectionId.clear();
        secName.clear();
        secLocation.clear();
        capacity.clear();
        securityLevel.getSelectionModel().clearSelection();
        status.getSelectionModel().clearSelection();
        shearchSectionField.clear();
    }
}
