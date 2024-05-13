package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Officer;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.OfficerRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import lk.ijse.gdse69.javafx.Util.Util;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddOfficerController extends MainDashBoard  implements Initializable {

    public AnchorPane MainAnchorPane;
    public TextField searchId;
    @FXML
    private TextField officerId;
    @FXML
    private TextField NIC;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML
    private TextField number;

    @FXML
    private TextField salery;

    @FXML
    private DatePicker DOB;

    @FXML
    private ComboBox<String> sectionId;


    @FXML
    private ComboBox<String> positionComboBox;

    @FXML
    private Text totalOfficerCount;
    @FXML
    private Text maleOfficerCount;
    @FXML
    private Text feMaleOfficerCount;
    @FXML
    private Text specialUnitCount;

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
        setOfficerCount();
        setToolTip();
        try {
            setComboBoxValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setNextOfficerId();
        setSearchOfficerIds();
    }

    private void setSearchOfficerIds() {
        List<String> offcerIds = new ArrayList<>();

        try {
            List<Officer> allOfficers = OfficerRepo.getAllOfficers();
            for (Officer officer : allOfficers) {
                offcerIds.add(officer.getOfficerId()+" - "+officer.getOfficerFirstName()+" "+officer.getOfficerLastName());
            }
            String[] possibleNames = offcerIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(searchId, possibleNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setNextOfficerId() {
        try {
            List<Officer> allOfficer = OfficerRepo.getAllOfficers();
            if (allOfficer.size() == 0){
                officerId.setText("O001");
            }
            else {
                int lastOfficerId = Integer.parseInt(allOfficer.get(allOfficer.size()-1).getOfficerId().substring(1));
                lastOfficerId++;
                if (lastOfficerId < 10){
                    officerId.setText("O00"+lastOfficerId);
                }
                else if (lastOfficerId < 100){
                    officerId.setText("O0"+lastOfficerId);
                }
                else {
                    officerId.setText("O"+lastOfficerId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        officerId.setEditable(false);
    }

    private void setComboBoxValues() throws SQLException {
        positionComboBox.getItems().addAll("Sergeant", "Lieutenant", "Captain", "Major", "Colonel", "General","Special Unit");
        gender.getItems().addAll(  "Male","Female");

        List<Section> jailSections = SectionRepo.getAllSections();

        for (Section section : jailSections){
            sectionId.getItems().add(section.getSectionId());
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
    private void setOfficerCount() {
        List<Officer> allOffisers = new ArrayList<>();

        try {
            allOffisers = OfficerRepo.getAllOfficers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (allOffisers != null) {
            totalOfficerCount.setText(String.valueOf(allOffisers.size()+" Officers"));

            long maleCount = allOffisers.stream().filter(officer -> officer.getGender().equals("Male")).count();
            maleOfficerCount.setText(String.valueOf(maleCount)+" Officers");

            long femaleCount = allOffisers.stream().filter(officer -> officer.getGender().equals("Female")).count();
            feMaleOfficerCount.setText(String.valueOf(femaleCount)+" Officers");

            specialUnitCount.setText(seUniCount(allOffisers)+" Officers");

        }
    }

    private String seUniCount(List<Officer> allOffisers) {
        long specialUnitCount = allOffisers.stream().filter(officer -> officer.getPosition().equals("Special Unit")).count();
        String specUniCount= String.valueOf(specialUnitCount);
        return specUniCount;
    }




    public void submitBtn(ActionEvent actionEvent) throws SQLException {


        if (checkEmptyFields()){
            if(Util.checkValidText(fName.getText(),"^[A-Za-z\\s'-]+$")){}else{
                ShowAlert.showErrorNotify("Invalid First Name. Ex : John");
                return;
            }
            if(Util.checkValidText(lName.getText(),"^[A-Za-z\\s'-]+$")){}else{
                ShowAlert.showErrorNotify("Invalid Last Name. Ex : Doe");
                return;
            }
            if (Util.checkValidText(NIC.getText(),"^[0-9]{9}[V]$")){}else {
                ShowAlert.showErrorNotify("Invalid NIC. Ex : 123456789V");
                return;
            }


            if (Double.valueOf(this.salery.getText())  > 0){}else {
                ShowAlert.showErrorNotify("Invalid Salery. Ex : XXXXX.XX");
                return;
            }


            if(Util.checkValidText(number.getText(),"^[0-9]{10}$")){}else {
                ShowAlert.showErrorNotify("Invalid Phone Number. Ex : 0712345678");
                return;
            }

            if(Util.checkValidText(email.getText(),"^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")){}else {
                ShowAlert.showErrorNotify("Invalid Email. Ex :XXXXX@gmail.com.");
                return;
            }

            if (DOB.getValue().isBefore(java.time.LocalDate.now())){}else {
                ShowAlert.showErrorNotify("Invalid Date of Birth");
                return;
            }

            Officer officer = new Officer(officerId.getText(),fName.getText(),lName.getText(),java.sql.Date.valueOf(DOB.getValue()),NIC.getText(),gender.getSelectionModel().getSelectedItem(),address.getText(),email.getText(),number.getText(),positionComboBox.getSelectionModel().getSelectedItem(),sectionId.getSelectionModel().getSelectedItem(),Double.parseDouble(salery.getText()));

            if (OfficerRepo.save(officer)) {

                ShowAlert.showSuccessNotify("Officer Added Successfully");
                clearFields();
            } else {
                ShowAlert.showErrorNotify("Failed to Add Officer");
            }
        }else {
            ShowAlert.showErrorNotify("Please Fill All Fields");
        }
    }

    private void clearFields() {
        officerId.clear();
        NIC.clear();
        fName.clear();
        lName.clear();
        gender.getSelectionModel().clearSelection();
        address.clear();
        DOB.getEditor().clear();
        email.clear();
        number.clear();
        salery.clear();
        positionComboBox.getSelectionModel().clearSelection();
        sectionId.getSelectionModel().clearSelection();
        setNextOfficerId();

    }

    public void canselBtn(ActionEvent actionEvent) {
        clearFields();
    }

    public boolean checkEmptyFields() {
        return Util.checkEmptyFields(officerId.getText(),NIC.getText(), fName.getText(), lName.getText(),
                gender.getSelectionModel().getSelectedItem(), address.getText(), email.getText(), number.getText(), salery.getText(), String.valueOf(DOB.getValue()),positionComboBox.getSelectionModel().getSelectedItem(),sectionId.getSelectionModel().getSelectedItem());
    }

    public void searchIdField(ActionEvent actionEvent) {
    }
}
