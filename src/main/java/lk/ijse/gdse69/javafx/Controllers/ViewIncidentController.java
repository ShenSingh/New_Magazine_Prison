package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Incident;
import lk.ijse.gdse69.javafx.Repository.IncidentRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewIncidentController extends MainDashBoard implements Initializable{
    public AnchorPane MainAnchorPane;

    public TableColumn<Incident, String> tvIncidentId;
    public TableColumn<Incident, String> tvSectionId;
    public TableColumn<Incident, String> tvType;
    public TableColumn<Incident, String> tvDate;
    public TableColumn<Incident, String> tvTime;
    public TableColumn<Incident, String> tvDescription;

    public JFXComboBox<String> viewOptionCombo;
    public Text totalSection;

    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;

    public TextField searchId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewOptionCombo.getItems().addAll("All Incidents");
        viewOptionCombo.setVisible(false);
        try {
            totalSection.setText(String.valueOf(SectionRepo.getAllSections().size()) + " Sections");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setTableValues(IncidentRepo.getAllIncidents());
        setToolTip();
        setSearchIds();
    }

    private void setSearchIds() {
        List<String> incidentIds = new ArrayList<>();

        List<Incident> allIncident =IncidentRepo.getAllIncidents();
        for (Incident incident : allIncident) {
            incidentIds.add(incident.getIncidentId()+" - "+incident.getIncidentType());
        }
        String[] possibleNames = incidentIds.toArray(new String[0]);

        TextFields.bindAutoCompletion(searchId, possibleNames);
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

    private void setTableValues(List<Incident> allIncidents) {

        if (allIncidents != null){
            tvIncidentId.setCellValueFactory(new PropertyValueFactory<>("incidentId"));
            tvSectionId.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
            tvType.setCellValueFactory(new PropertyValueFactory<>("incidentType"));
            tvDate.setCellValueFactory(new PropertyValueFactory<>("incidentDate"));
            tvTime.setCellValueFactory(new PropertyValueFactory<>("incidentTime"));
            tvDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

            tvDate.getTableView().setItems(FXCollections.observableArrayList(allIncidents));
        }
    }

    public void searchIdField(ActionEvent actionEvent) throws IOException {
        String id = searchId.getText().split(" - ")[0];
        SearchId.setIncidentId(id);

        createStage("/View/IncidentSetting.fxml");
    }
}
