package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewSectionController extends MainDashBoard implements Initializable {


    public TableColumn<Section, String> TVsectionId;
    public TableColumn<Section, String> TVcapacity;
    public TableColumn<Section, String> TVlocation;
    public TableColumn<Section, String> TVname;
    public TableColumn<Section, String> TVsecurityLevel;
    public TableColumn<Section, String> TVstatus;
    @FXML
    private JFXComboBox<String> viewOptionCombo;

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
        viewOptionCombo.setItems(FXCollections.observableArrayList("All", "Jails","Active"));

        try {
            setTableDate(SectionRepo.getAllSections());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setToolTip();

        setTotalCount();

        viewOptionCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("All")){
                try {
                    setTableDate(SectionRepo.getAllSections());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            else if (newValue.equals("Jails")) {
                try {
                    setTableDate(SectionRepo.getJailSections());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (newValue.equals("Active")) {
                try {
                    setTableDate(SectionRepo.getSectionByActive());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

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

    private void setTableDate(List<Section> sections) {
        if (sections != null){
            TVsectionId.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
            TVname.setCellValueFactory(new PropertyValueFactory<>("sectionName"));
            TVlocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            TVcapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
            TVsecurityLevel.setCellValueFactory(new PropertyValueFactory<>("securityLevel"));
            TVstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            TVstatus.getTableView().setItems(FXCollections.observableArrayList(sections));
        }
    }
}
