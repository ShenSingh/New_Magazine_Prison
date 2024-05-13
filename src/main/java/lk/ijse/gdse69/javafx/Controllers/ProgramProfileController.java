package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Program;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.ProgramRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramProfileController extends MainDashBoard implements Initializable {
    public AnchorPane MainAnchorPane;

    public TextField programId;
    public TextField programName;
    public ComboBox<String> sectionId;
    public TextField programTime;
    public DatePicker programDate;
    public TextField description;

    public Text fullProgramName;

    public TextField searchField;


    public Text OPsectionId;
    public Text OPsectionName;
    public Text OPlocation;
    public Text OPcapacity;
    public Text OPsecurityLevel;
    public Text OPstatus;

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
        setToolTip();
        setSearchIds();

        try {
            for (Section section : SectionRepo.getAllSections()) {
                sectionId.getItems().add(section.getSectionId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        programId.setEditable(false);
    }

    private void setSearchIds() {
        List<String> programIds = new ArrayList<>();

        try {
            List<Program> allPograms = ProgramRepo.getAllPrograms();
            for (Program program : allPograms) {
                programIds.add(program.getProgramId()+" - "+program.getProgramName());
            }
            String[] possibleNames = programIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(searchField, possibleNames);
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

    public void saveBtn(ActionEvent actionEvent) throws SQLException {
        String id =searchField.getText().split(" - ")[0];

        if (!id.isEmpty()){


            if (checkEmptyFields()) {
            if (checkTimeValidation()){}else {return;}


                    //search program
                    String programId = this.programId.getText();
                    String programName = this.programName.getText();
                    String sectionId = this.sectionId.getSelectionModel().getSelectedItem();
                    LocalDate programDate = this.programDate.getValue();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
                    String time = this.programTime.getText();
                    LocalTime programTime = LocalTime.parse(time, formatter);

                    String description = this.description.getText();

                    Program program = new Program(programId, programName, sectionId, programDate, programTime, description);

                    boolean isUpdated = ProgramRepo.update(program);

                    if (isUpdated) {
                        ShowAlert.showSuccessNotify("Program Updated Successfully");

                    } else {
                        ShowAlert.showErrorNotify("Program Not Updated");
                    }
            } else {
                ShowAlert.showErrorNotify("Please Fill All Fields");
            }
        }
    }

    private boolean checkTimeValidation() {
        String time = this.programTime.getText().trim();

        if (time.matches("^(?:[01]\\d|2[0-3]).(?:[0-5]\\d)$")){
            return true;
        }
        ShowAlert.showErrorNotify("Invalid Time Format. Ex : 12.00");
        return false;
    }

    public void cancelBtn(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        programId.clear();
        programName.clear();
        sectionId.getSelectionModel().clearSelection();
        programTime.clear();
        programDate.setValue(null);
        description.clear();
        searchField.clear();
        clearSection();
    }

    private void clearSection() {
        OPsectionId.setText("");
        OPsectionName.setText("");
        OPlocation.setText("");
        OPcapacity.setText("");
        OPsecurityLevel.setText("");
        OPstatus.setText("");
    }

    public void seachProgram(ActionEvent actionEvent) throws SQLException {

        String id =searchField.getText().split(" - ")[0];

        if (!id.isEmpty()){

            Program program = ProgramRepo.search(id);

            if (program != null){
                setSection(program.getSectionId());
                fullProgramName.setText(program.getProgramName());
                programId.setText(program.getProgramId());
                programName.setText(program.getProgramName());
                sectionId.getSelectionModel().select(program.getSectionId());

                LocalTime programTimes = program.getProgramTime(); // Assuming programTime is a LocalTime object
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
                String formattedTime = programTimes.format(formatter);
                programDate.setValue(program.getProgramDate());
                programTime.setText(formattedTime);

                description.setText(program.getDescription());
            }else {
                ShowAlert.showErrorNotify("Program Not Found");
            }
        }else {
            ShowAlert.showErrorNotify("Please Enter Program Id");
        }
    }

    private void setSection(String sectionId) {
        try {
            Section section = SectionRepo.search(sectionId);

            OPsectionId.setText(section.getSectionId());
            OPsectionName.setText(section.getSectionName());
            OPlocation.setText(section.getLocation());
            OPcapacity.setText(String.valueOf(section.getCapacity()));
            OPsecurityLevel.setText(section.getSecurityLevel());
            OPstatus.setText(section.getStatus());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkEmptyFields() {
        if (programId.getText().isEmpty() || programName.getText().isEmpty() || sectionId.getSelectionModel().getSelectedItem() == null || programTime.getText().isEmpty() || programDate.getValue() == null || description.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public void editProfileTogal(ActionEvent actionEvent) {
        isEdit = !isEdit;

        programName.setEditable(isEdit);
        sectionId.setEditable(isEdit);
        programTime.setEditable(isEdit);
        programDate.setEditable(isEdit);
        description.setEditable(isEdit);
    }

    public void deleteProgram(ActionEvent actionEvent) throws SQLException {
        String id=searchField.getText().split(" - ")[0];;

        if (!id.isEmpty()) {
            boolean isDeleted = ProgramRepo.delete(id);

            if (isDeleted) {
                ShowAlert.showSuccessNotify("Program Deleted Successfully");
                clearFields();
                setSearchIds();
            } else {
                ShowAlert.showErrorNotify("Program Not Deleted");
            }
        }
    }
}