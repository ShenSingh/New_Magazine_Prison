package lk.ijse.gdse69.javafx.Controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Incident;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Model.SetIncidentRecord;
import lk.ijse.gdse69.javafx.Repository.IncidentRepo;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import lk.ijse.gdse69.javafx.Repository.SetIncidentRecordRepo;
import lk.ijse.gdse69.javafx.Util.Util;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddIncidentController extends MainDashBoard implements Initializable {
    public AnchorPane MainAnchorPane;

    public Text totalSection;

    public TableColumn<String, String> riInmateId;

    public TextField incidentId;
    public ComboBox<String> sectionId;
    public ComboBox<String> incidentType;
    public TextField description;
    public TextField time;
    public TextField inmateId;


    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;

    public TextField searchIncidentId;

    public static String incId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setToolTip();

        setComboBoxValues();
        setInmateIds();
        setIncidentIds();
        setNextIncidentId();

        try {
            totalSection.setText(String.valueOf(SectionRepo.getAllSections().size())+" Section");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setIncidentIds() {
        List<String> incidentIds = new ArrayList<>();

        List<Incident> allIncident =IncidentRepo.getAllIncidents();
        for (Incident incident : allIncident) {
            incidentIds.add(incident.getIncidentId()+" - "+incident.getIncidentType());
        }
        String[] possibleNames = incidentIds.toArray(new String[0]);

        TextFields.bindAutoCompletion(searchIncidentId, possibleNames);
    }

    private void setNextIncidentId() {
        try {
            String lastId = IncidentRepo.getLastId();
            if(lastId == null){
                incidentId.setText("ID001");
            }else{
                int id = Integer.parseInt(lastId.substring(2));
                id++;
                if(id<10){
                    incidentId.setText("ID00"+id);
                }else if(id<100){
                    incidentId.setText("ID0"+id);
                }else{
                    incidentId.setText("ID"+id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        incidentId.setEditable(false);
    }

    private void setInmateIds() {
        List<String> inmateIds = new ArrayList<>();

        List<Inmate> allInmate = null;
        try {
            allInmate = InmateRepo.getAllInmates();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Inmate inmate : allInmate) {
            inmateIds.add(inmate.getInmateId()+" - "+inmate.getInmateFirstName()+" "+inmate.getInmateLastName());
        }
        String[] possibleNames = inmateIds.toArray(new String[0]);

        TextFields.bindAutoCompletion(inmateId, possibleNames);

    }

    private void setComboBoxValues() {
        incidentType.getItems().addAll("Assaults", "Contraband","Suicide or Self-Harm","Security Breaches");
        try {
            List<Section> allSections = SectionRepo.getAllSections();
            List<String> sectionIds = new ArrayList<>();
            for (Section section : allSections) {
                sectionIds.add(section.getSectionId());
            }
            sectionId.getItems().addAll(sectionIds);
        } catch (Exception e) {
            e.printStackTrace();
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

    private List<String> inmateIds = new ArrayList<>();
    public void canselBtn(ActionEvent actionEvent) {
        clearFields();
        setNextIncidentId();
    }

    public void submitBtn(ActionEvent actionEvent) {
        if (checkEmptyFields()){
            if (Util.validateTime(time.getText())){
                createIncidentOjt();
            }else{
                ShowAlert.showErrorNotify("Invalid Time Format. Please Enter Valid Time EX : XX:XX.");
                return;
            }
        }else{
            ShowAlert.showErrorNotify("Please Fill All Fields.");
        }
    }

    private void createIncidentOjt() {
        String id = incidentId.getText();
        String secId = sectionId.getSelectionModel().getSelectedItem();
        String incType = incidentType.getSelectionModel().getSelectedItem();
        String desc = description.getText();
        String t = time.getText();
        LocalDate date = LocalDate.now();

        Incident incident = new Incident(id,secId,incType,date,t,desc);
        List<String> ids = inmateIds;

        SetIncidentRecord setIncidentRecord = new SetIncidentRecord(incident,ids);

        boolean isAdded = false;
        try {
            isAdded = SetIncidentRecordRepo.save(setIncidentRecord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isAdded) {
            clearFields();
            ShowAlert.showSuccessNotify("Incident Added Successfully.");
        }else {
            ShowAlert.showErrorNotify("Failed to Add Incident.");
        }
    }
    private void clearFields() {
        incidentId.clear();
        sectionId.getSelectionModel().clearSelection();
        incidentType.getSelectionModel().clearSelection();
        description.clear();
        time.clear();
        inmateId.clear();
        inmateIds.clear();
        setTableValues(inmateIds);
        setNextIncidentId();
    }

    private boolean checkEmptyFields(){
        return Util.checkEmptyFields(incidentId.getText(),sectionId.getSelectionModel().getSelectedItem(),incidentType.getSelectionModel().getSelectedItem(),time.getText());
    }

    public void onInmateIds(ActionEvent actionEvent) {
        String id = this.inmateId.getText();
        if (id != null){
            int index = inmateId.getText().indexOf(" ");
            String idS = inmateId.getText().substring(0, index); // Extract the substring before the first space
            System.out.println(idS);
            inmateIds.add(idS);
            setTableValues(inmateIds);
            inmateId.clear();
        }else{
            ShowAlert.showErrorNotify("Please Enter Inmate ID.");
        }
    }

    private void setTableValues(List<String> inmateIds) {
        riInmateId.setCellValueFactory(cellData -> {
            String value = cellData.getValue(); // Each cell represents a String value
            return new ReadOnlyStringWrapper(value);
        });
        ObservableList<String> items = FXCollections.observableArrayList(inmateIds);
        riInmateId.getTableView().setItems(items);
    }

    public void searchIncidentIdOnAction(ActionEvent actionEvent) throws IOException {
        incId = searchIncidentId.getText();

        if (incId != null){
            createStage("/View/IncidentSetting.fxml");
        }
    }

    public static String getIncId(){
        return incId;
    }

    public void incidentOnAction(ActionEvent actionEvent) {
    }

    public void descriptiononAction(ActionEvent actionEvent) {
        submitBtn(actionEvent);
    }
}
