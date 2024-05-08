package lk.ijse.gdse69.javafx.Controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;

import java.net.URL;
import java.sql.SQLException;
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
    }

    public void submitBtn(ActionEvent actionEvent) {
    }

    public void onInmateIds(ActionEvent actionEvent) {
        List<String> inmateIds = new ArrayList<>();



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
