package lk.ijse.gdse69.javafx.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SectionPageController extends MainDashBoard implements Initializable {


    public TableColumn<Section, String> TVsectionId;
    public TableColumn<Section, String> TVname;
    public TableColumn<Section, String> TVlocation;
    public TableColumn<Section, String> TVcapacity;
    public TableColumn<Section, String> TVsecurityLevel;
    public TableColumn<Section, String> TVstatus;
    public AnchorPane MainAnchorPane;
    public TextField searchId;
    @FXML
    private Text totalSection;
    @FXML
    private Text activeSectionCount;
    @FXML
    private Text jailSectionCount;
    @FXML
    private Text highSecuritySecCount;

    private static String id;


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

        visitorBtn.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                try {
                    setShortCutKey(newScene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setSectionCount();
        try {
            setTableValues(SectionRepo.getAllSections());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setToolTip();
        setSearchIds();
    }
    private void setShortCutKey(Scene scene) throws IOException {

        if (scene == null) {
            System.out.println("scene is null");
        }else {
            scene.setOnKeyPressed(event -> {
                if (new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + d");
                    try {
                        createStage("/View/InmatePage.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + o");
                    try {
                        createStage("/View/OfficerPage.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + d");
                    try {
                        createStage("/View/DashBoard.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + s");
                    try {
                        createStage("/View/DashBoard.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + e");
                    try {
                        createStage("/View/financialPage.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

    }

    private void setSearchIds() {
        List<String> sectionIds = new ArrayList<>();

        try {
            List<Section> allSections = SectionRepo.getAllSections();
            for (Section section : allSections) {
                sectionIds.add(section.getSectionId()+" - "+section.getSectionName());
            }
            String[] possibleNames = sectionIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(searchId, possibleNames);
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
    private void setTableValues(List<Section> allSections) {

        if (allSections != null){
            TVsectionId.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
            TVname.setCellValueFactory(new PropertyValueFactory<>("sectionName"));
            TVlocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            TVcapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
            TVsecurityLevel.setCellValueFactory(new PropertyValueFactory<>("securityLevel"));
            TVstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            TVstatus.getTableView().setItems(FXCollections.observableArrayList(allSections));
        }

    }

    private void setSectionCount() {
        List<Section> allSection = new ArrayList<>();

        try {
           allSection = SectionRepo.getAllSections();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(allSection != null){

        totalSection.setText(String.valueOf(allSection.size())+ " Section");

        long activeCount = allSection.stream().filter(section -> section.getStatus().equals("Active")).count();
        String totalActive = String.valueOf(activeCount);
        String activeC= totalActive + " Section";
        activeSectionCount.setText(activeC);

        highSecuritySecCount.setText(setHighSecurityCount(allSection));

        long jailCount;
        try {
            jailCount = SectionRepo.getJailSections().size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String totalJail = String.valueOf(jailCount);
        String jailC= totalJail + " Section";
        jailSectionCount.setText(jailC);


        }else {
            System.out.println("allSection Is Null");
        }

    }

    private String setHighSecurityCount(List<Section> allSection) {
        long highSecuCount = allSection.stream().filter(section -> section.getSecurityLevel().equals("High")).count();
        String totalHigh = String.valueOf(highSecuCount);
        String highC= totalHigh + " Section";
        return highC;
    }

    public void activeSectionBtn(ActionEvent actionEvent) throws SQLException {

        setTableValues(SectionRepo.getSectionByActive());
    }

    public void jailSectionBtn(ActionEvent actionEvent) throws SQLException {
        setTableValues(SectionRepo.getJailSections());
    }

    public void highSecuritySection(ActionEvent actionEvent) throws SQLException {
        setTableValues(SectionRepo.getSectionByHighSecurity());
    }

    public void searchIdField(ActionEvent actionEvent) throws IOException {
        id = searchId.getText().split(" - ")[0];
        createStage("/View/SectionProfile.fxml");
    }
    public static String getId(){
        return id;
    }
}
