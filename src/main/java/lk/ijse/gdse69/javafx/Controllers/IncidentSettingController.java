package lk.ijse.gdse69.javafx.Controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Incident;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.IncidentRelatedInmateRepo;
import lk.ijse.gdse69.javafx.Repository.IncidentRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import lk.ijse.gdse69.javafx.Util.Util;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IncidentSettingController extends MainDashBoard implements Initializable {

    public TextField searchField;

    public AnchorPane MainAnchorPane;
    public ComboBox<String> incidentType;
    public TableColumn<String, String> riInmateId;
    public TextField incidentId;
    public TextField sectionId;
    public DatePicker incidentDate;
    public TextField incidentTime;
    public TextField description;
    public TextField inmateIds;

    private boolean isEdit = false;

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
            incidentType.getItems().addAll("Assaults", "Contraband","Suicide or Self-Harm","Security Breaches");
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

    public void saveBtn(ActionEvent actionEvent) {
        if (Util.checkEmptyFields(incidentId.getText(),sectionId.getText(),incidentTime.getText(),description.getText()) && incidentDate.getValue() != null){
            if (Util.checkValidText(incidentId.getText(), "ID\\d{3}")){}else {
                new ShowAlert("Error", "Invalid Id","Please Enter Valid Incident Id EX: IDXXX.", Alert.AlertType.ERROR);
                return;
            }
            if (Util.checkValidText(sectionId.getText(), "S\\d{3}")){}else {
                new ShowAlert("Error", "Invalid Id","Please Enter Valid Section Id EX: SXXX.", Alert.AlertType.ERROR);
                return;
            }
            if(checkSectionId(sectionId.getText())){}else{
                new ShowAlert("Error", "Not Found","Section Id Is Not Found.", Alert.AlertType.ERROR);
                return;
            }

            if (Util.validateTime(incidentTime.getText())){}else {
                new ShowAlert("Error", "Invalid Time","Please Enter Valid Time EX: XX:XX.", Alert.AlertType.ERROR);
                return;
            }

            String id = incidentId.getText();
            String secId = sectionId.getText();
            String type = incidentType.getSelectionModel().getSelectedItem();
            LocalDate date = incidentDate.getValue();
            String time = incidentTime.getText();
            String desc = description.getText();

            Incident incident = new Incident(id, secId, type, date, time, desc);

            try {
                boolean isUpdate = IncidentRepo.update(incident);
                if (isUpdate){
                    new ShowAlert("Success","Incident Updated","Incident Update Successfully", Alert.AlertType.INFORMATION);
                    clearFields();
                }else {
                    new ShowAlert("Failed","Incident Updated","Incident Updated Failed", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean checkSectionId(String text) {
        List<Section> sections = new ArrayList<>();

        try {
            sections = SectionRepo.getAllSections();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Section section : sections) {
            if (section.getSectionId().equals(text)){
                return true;
            }
        }
        return false;
    }

    public void cancelBtn(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        incidentId.clear();
        sectionId.clear();
        incidentDate.getEditor().clear();
        incidentTime.clear();
        description.clear();
        incidentType.getSelectionModel().clearSelection();
        inmateIds.clear();
    }

    public void deleteIncident(ActionEvent actionEvent) throws SQLException {
        String id = searchField.getText();

        if (id != null){
            if (Util.checkValidText(id,"ID\\d{3}")){
                boolean isDeleted = IncidentRepo.delete(id);
                if (isDeleted) {
                    new ShowAlert("Success","Incident Delete","Incident Deleted Successfully", Alert.AlertType.INFORMATION);
                    clearFields();
                }else {
                    new ShowAlert("Failed","Incident Delete","Incident Delete Failed", Alert.AlertType.ERROR);
                }
            }else{
                new ShowAlert("Invalid ID","Incident ID","Please Enter a Valid Incident ID", Alert.AlertType.ERROR);
            }
        }else{
            new ShowAlert("Empty Field","Search Field","Please Enter a Incident ID", Alert.AlertType.ERROR);
        }
    }

    public void editProfileTogal(ActionEvent actionEvent) {
        isEdit = !isEdit;

        incidentId.setEditable(isEdit);
        sectionId.setEditable(isEdit);
        incidentDate.setEditable(isEdit);
        incidentTime.setEditable(isEdit);
        description.setEditable(isEdit);
        incidentType.setDisable(!isEdit);
        inmateIds.setEditable(isEdit);
    }

    public void onInmateIds(ActionEvent actionEvent) {
    }

    public void searchIncident(ActionEvent actionEvent) {
        String id = searchField.getText();

        if (!id.isEmpty()){
            if (Util.checkValidText(id,"ID\\d{3}")){
                try {
                    setValuesFields(IncidentRepo.search(id));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }else {
            new ShowAlert("Empty Field","Search Field","Please Enter a Incident ID", Alert.AlertType.ERROR);
        }
    }

    private void setValuesFields(Incident incident) {

        if (incident != null){
            incidentId.setText(incident.getIncidentId());
            sectionId.setText(incident.getSectionId());
            incidentDate.setValue(LocalDate.parse(incident.getIncidentDate().toString()));

            LocalTime time = LocalTime.parse(incident.getIncidentTime());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = time.format(formatter);
            incidentTime.setText(formattedTime);

            description.setText(incident.getDescription());
            incidentType.setValue(incident.getIncidentType());

            setInmateIds(IncidentRelatedInmateRepo.getInmateIds(incident.getIncidentId()));



        }else {
            new ShowAlert("Failed","Incident Search","Incident Not Found", Alert.AlertType.ERROR);
        }

    }

    private void setInmateIds(List<String> inmateIds) {
        if (inmateIds != null) { // Check if inmateIds is not null
            riInmateId.setCellValueFactory(cellData -> {
                String value = String.valueOf(cellData.getValue()); // Each cell represents a String value
                return new ReadOnlyStringWrapper(value);
            });
            ObservableList<String> items = FXCollections.observableArrayList(inmateIds);
            riInmateId.getTableView().setItems(items);
        } else {
            // Handle the case where inmateIds is null
            System.out.println("Error: inmateIds is null");
        }
    }
}
