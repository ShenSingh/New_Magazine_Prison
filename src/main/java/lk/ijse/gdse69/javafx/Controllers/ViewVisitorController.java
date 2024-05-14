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
import lk.ijse.gdse69.javafx.Model.Visitor;
import lk.ijse.gdse69.javafx.Repository.VisitorRepo;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewVisitorController extends MainDashBoard implements Initializable {


    public TableColumn<Visitor, String> tvVisitorId;
    public TableColumn<Visitor, String> tvFName;
    public TableColumn<Visitor, String> tvLName;
    public TableColumn<Visitor, String> tvDOB;
    public TableColumn<Visitor, String> tvGender;
    public TableColumn<Visitor, String> tvNIC;
    public TableColumn<Visitor, String> tvAddress;
    public TableColumn<Visitor, String> tvNumber;
    public TableColumn<Visitor, String> tvType;
    public AnchorPane MainAnchorPane;

    public TextField searchId;

    public Text familyCount;
    public Text legalRepresentativeCount;
    public Text officialsCount;
    public Text otherCount;

    @FXML
    private JFXComboBox<String> viewVisitorOption;

    @FXML
    private Text totalVisitorCount;

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

        try {
            String count=String.valueOf(VisitorRepo.getAllVisitors().size());
            totalVisitorCount.setText(count+" Visitors");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            setTableValues(VisitorRepo.getAllVisitors());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        viewVisitorOption.getItems().addAll("All", "Male", "Female");

        viewVisitorOption.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("All")){
                try {
                    setTableValues(VisitorRepo.getAllVisitors());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            else if (newValue.equals("Male")) {
                try {
                    setTableValues(VisitorRepo.getVisitorByGender("Male"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (newValue.equals("Female")) {
                try {
                    setTableValues(VisitorRepo.getVisitorByGender("Female"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setToolTip();
        settotalCount();
        setSearchIds();
    }

    private void setSearchIds() {
        List<String> visitorIds = new ArrayList<>();

        try {
            List<Visitor> allVisitors = VisitorRepo.getAllVisitors();
            for (Visitor visitor : allVisitors) {
                visitorIds.add(visitor.getVisitorID()+" - "+visitor.getVisitorFirstName()+" "+visitor.getVisitorLastName());
            }
            String[] possibleNames = visitorIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(searchId, possibleNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void settotalCount() {
        try {
            familyCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Family").size())+" Visitors");
            legalRepresentativeCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Legal Representative").size())+" Visitors");
            officialsCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Officials").size())+" Visitors");
            otherCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Other").size())+" Visitors");
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

    private void setTableValues(List<Visitor> allVisitors) {
        if (allVisitors != null){
            tvVisitorId.setCellValueFactory(new PropertyValueFactory<>("visitorID"));
            tvFName.setCellValueFactory(new PropertyValueFactory<>("visitorFirstName"));
            tvLName.setCellValueFactory(new PropertyValueFactory<>("visitorLastName"));
            tvDOB.setCellValueFactory(new PropertyValueFactory<>("visitorDOB"));
            tvNIC.setCellValueFactory(new PropertyValueFactory<>("visitorNIC"));
            tvGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            tvAddress.setCellValueFactory(new PropertyValueFactory<>("visitorAddress"));
            tvNumber.setCellValueFactory(new PropertyValueFactory<>("visitorNumber"));
            tvType.setCellValueFactory(new PropertyValueFactory<>("visitorType"));

            tvAddress.getTableView().setItems(FXCollections.observableArrayList(allVisitors));
        }
    }

    public void searchIdField(ActionEvent actionEvent) throws IOException {
        String id = searchId.getText().split(" - ")[0];
        SearchId.setVisitorId(id);
        createStage("/View/VisitorProfile.fxml");
    }
}
