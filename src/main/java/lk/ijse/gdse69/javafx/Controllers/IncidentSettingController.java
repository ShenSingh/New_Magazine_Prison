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
import lk.ijse.gdse69.javafx.Repository.IncidentRelatedInmateRepo;
import lk.ijse.gdse69.javafx.Repository.IncidentRepo;
import lk.ijse.gdse69.javafx.Util.Util;
import org.controlsfx.control.textfield.TextFields;

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
        incidentId.setEditable(false);
        sectionId.setEditable(false);
        
        setSearchIds();
    }

    private void setSearchIds() {
        List<String> incidentIds = new ArrayList<>();

        List<Incident> allIncident =IncidentRepo.getAllIncidents();
        for (Incident incident : allIncident) {
            incidentIds.add(incident.getIncidentId()+" - "+incident.getIncidentType());
        }
        String[] possibleNames = incidentIds.toArray(new String[0]);

        TextFields.bindAutoCompletion(searchField, possibleNames);
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
            if (Util.validateTime(incidentTime.getText())){}else {
                ShowAlert.showErrorNotify("Invalid Time Format. EX : XX:XX");
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
                    ShowAlert.showSuccessNotify("Incident Updated Successfully.");
                    setSearchIds();
                }else {
                    ShowAlert.showErrorNotify("Failed to Update Incident.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
    }

    public void deleteIncident(ActionEvent actionEvent) throws SQLException {
        if (searchField.getText().isEmpty()){
            ShowAlert.showErrorNotify("Please enter Incident ID");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirmation Delete");
        alert.setHeaderText("Delete Incident");
        alert.setContentText("Are you sure you want to delete this Incident?");

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);

        okButton.setOnAction(e -> {
            goDeleteIncident();
            alert.close();
        });
        cancelButton.setOnAction(e -> {
            alert.close();
        });

        alert.showAndWait();
    }
    private void goDeleteIncident(){

    }
    public void editProfileTogal(ActionEvent actionEvent) {
        isEdit = !isEdit;

        incidentDate.setEditable(isEdit);
        incidentTime.setEditable(isEdit);
        description.setEditable(isEdit);
        incidentType.setDisable(!isEdit);
    }
    
    public void searchIncident(ActionEvent actionEvent) {
        String id = searchField.getText().split(" - ")[0];;

        if (!id.isEmpty()){
            if (Util.checkValidText(id,"ID\\d{3}")){
                try {
                    setValuesFields(IncidentRepo.search(id));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }else {
            ShowAlert.showErrorNotify("Please Enter a Incident ID.");
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
            ShowAlert.showErrorNotify("Incident Not Found.");
        }

    }

    private void setInmateIds(List<String> inmateIds) {  // table data set //
        if (inmateIds != null) {
            riInmateId.setCellValueFactory(cellData -> {
                String value = String.valueOf(cellData.getValue());
                return new ReadOnlyStringWrapper(value);
            });
            ObservableList<String> items = FXCollections.observableArrayList(inmateIds);
            riInmateId.getTableView().setItems(items);
        }

    }
}
