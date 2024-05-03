package lk.ijse.gdse69.javafx.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionPageController extends MainDashBoard{


    public TableColumn<Section, String> TVsectionId;
    public TableColumn<Section, String> TVname;
    public TableColumn<Section, String> TVlocation;
    public TableColumn<Section, String> TVcapacity;
    public TableColumn<Section, String> TVsecurityLevel;
    public TableColumn<Section, String> TVstatus;
    @FXML
    private Text totalSection;
    @FXML
    private Text activeSectionCount;
    @FXML
    private Text jailSectionCount;
    @FXML
    private Text highSecuritySecCount;





    public void initialize(){
        setSectionCount();
        try {
            setTableValues(SectionRepo.getAllSections());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        totalSection.setText(String.valueOf(allSection.size()));

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
}
