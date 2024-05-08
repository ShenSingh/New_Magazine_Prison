package lk.ijse.gdse69.javafx.Controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Incident;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Model.SetIncidentRecord;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import lk.ijse.gdse69.javafx.Repository.SetIncidentRecordRepo;
import lk.ijse.gdse69.javafx.Util.Util;

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
    public TextField sectionId;
    public ComboBox<String> incidentType;
    public TextField description;
    public TextField time;
    public TextField inmateId;

    private List<String> inmateIds = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        incidentType.getItems().addAll("Assaults", "Contraband","Suicide or Self-Harm","Security Breaches");
        try {
            totalSection.setText(String.valueOf(SectionRepo.getAllSections().size())+" Section");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void canselBtn(ActionEvent actionEvent) {
        clearFields();
    }

    public void submitBtn(ActionEvent actionEvent) {

        if (checkEmptyFields()){
            if (Util.checkValidText(incidentId.getText(),"ID\\d{3}")){
                if (Util.checkValidText(sectionId.getText(),"S\\d{3}")){
                    
                    if (checkSectionId()){}else {return;}
                    
                    if (Util.validateTime(time.getText())){
                        createIncidentOjt();
                    }else{
                        new ShowAlert("Invalid Time", "Invalid Time Type","Please Enter Valid Time. EX : XX:XX.", Alert.AlertType.INFORMATION);
                        return;
                    }
                }
            }else {
                new ShowAlert("Invalid Id", "Invalid ID Type","Please Enter Valid Id. EX : IDXXX.", Alert.AlertType.ERROR);
            }
        }else {
            new ShowAlert("Empty Fields", "Empty Fields","Please fill All fields.", Alert.AlertType.INFORMATION);
        }
    }

    private void createIncidentOjt() {
        String id = incidentId.getText();
        String secId = sectionId.getText();
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
            new ShowAlert("Success", "Incident Added", "Incident Added Successfully.", Alert.AlertType.INFORMATION);
        }else {
            new ShowAlert("Failed", "Incident Added", "Incident Added Failed.", Alert.AlertType.ERROR);
        }


    }

    private void clearFields() {
        incidentId.clear();
        sectionId.clear();
        incidentType.getSelectionModel().clearSelection();
        description.clear();
        time.clear();
        inmateId.clear();
        inmateIds.clear();
    }

    private boolean checkSectionId() {
        List<Section> sections = new ArrayList<>();
        try {
            sections = SectionRepo.getAllSections();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        for (Section section : sections) {
            if (section.getSectionId().equals(sectionId.getText())){
                return true;
            }
        }
        return false;
    }

    private boolean checkEmptyFields(){
        return Util.checkEmptyFields(incidentId.getText(),sectionId.getText(),incidentType.getSelectionModel().getSelectedItem(),time.getText());
    }

    public void onInmateIds(ActionEvent actionEvent) {

        String id = this.inmateId.getText();

        if (id != null){
            if (Util.checkValidText(id,"I\\d{3}")){
                if (checkInmateId()){
                    inmateIds.add(id);
                    setTableValues(inmateIds);
                    inmateId.clear();
                }
            }else{
            new ShowAlert("Invalid Id", "Invalid ID Type","Please Enter Valid Id. EX : IXXX.", Alert.AlertType.ERROR);

            }
        }else{
            new ShowAlert("Empty Field", "Empty Inmate Id Field","Please Enter Inmate Id.", Alert.AlertType.INFORMATION);
        }


    }

    private boolean checkInmateId() {
        List<Inmate> inmates = new ArrayList<>();

        try {
            inmates = InmateRepo.getAllInmates();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Inmate inmate : inmates) {
            if (inmate.getInmateId().equals(inmateId.getText())){
                return true;
            }
        }
        new ShowAlert("Invalid Id", "No Inmate","Please Enter Valid Inmate Id.", Alert.AlertType.ERROR);
        return false;
    }


    private void setTableValues(List<String> inmateIds) {
        riInmateId.setCellValueFactory(cellData -> {
            String value = cellData.getValue(); // Each cell represents a String value
            return new ReadOnlyStringWrapper(value);
        });
        ObservableList<String> items = FXCollections.observableArrayList(inmateIds);
        riInmateId.getTableView().setItems(items);
    }

}
