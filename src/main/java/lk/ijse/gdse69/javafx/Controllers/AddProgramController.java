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
import lk.ijse.gdse69.javafx.Util.Util;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddProgramController extends MainDashBoard implements Initializable {
    public Text totalSectionCount;

    public DatePicker programDate;
    public TextField programId;
    public TextField programName;
    public ComboBox<String> sectionId;
    public TextField programTime;
    public TextField programDescription;

    public AnchorPane MainAnchorPane;

    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;


    public TextField searchProgram;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            totalSectionCount.setText(String.valueOf(SectionRepo.getAllSections().size())+" Sections");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setToolTip();
        setNextProgramId();
        setSectionIds();
        setSearchIds();

        totalSectionCount.setText(sectionId.getItems().size()+" Sections");
    }

    private void setSearchIds() {
        List<String> programIds = new ArrayList<>();

        try {
            List<Program> allPograms = ProgramRepo.getAllPrograms();
            for (Program program : allPograms) {
                programIds.add(program.getProgramId()+" - "+program.getProgramName());
            }
            String[] possibleNames = programIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(searchProgram, possibleNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSectionIds() {
        List<Section> sections = new ArrayList<>();
        try {
            sections = SectionRepo.getAllSections();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Section section : sections) {
            sectionId.getItems().add(section.getSectionId());
        }
    }

    private void setNextProgramId() {
        List<Program> programs = new ArrayList<>();
        try {
            programs = ProgramRepo.getAllPrograms();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (programs.size() == 0) {
            programId.setText("P001");
        } else {
            String lastId = programs.get(programs.size() - 1).getProgramId();
            String id = lastId.substring(1);
            int newId = Integer.parseInt(id) + 1;
            if (newId < 10) {
                programId.setText("P00" + newId);
            } else if (newId < 100) {
                programId.setText("P0" + newId);
            } else {
                programId.setText("P" + newId);
            }
        }
        programId.setEditable(false);
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

    public void canselBtn(ActionEvent actionEvent) {

        clearFields();
    }

    private void clearFields() {
        programId.clear();
        programName.clear();
        sectionId.getSelectionModel().clearSelection();
        programDate.setValue(null);
        programTime.clear();
        programDescription.clear();
        setNextProgramId();
    }

    public void submitBtn(ActionEvent actionEvent) throws SQLException {

        if (checkEmptyFields()) {

            if (checkTimeValidation()){}else {return;}

            String programId = this.programId.getText();
            String programName = this.programName.getText();
            String sectionId = this.sectionId.getSelectionModel().getSelectedItem();
            LocalDate programDate = this.programDate.getValue();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
            String time = this.programTime.getText().trim();
            LocalTime programTime = LocalTime.parse(time, formatter);
            String description = programDescription.getText();

            Program program = new Program(programId, programName, sectionId, programDate, programTime, description);

            boolean isAdded = ProgramRepo.save(program);

            if (isAdded) {
                clearFields();
                ShowAlert.showSuccessNotify("Program Added Successfully");
            } else {
                ShowAlert.showErrorNotify("Program Not Added");
            }

        } else {
            ShowAlert.showErrorNotify("Please Fill All Fields");
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
    private boolean checkEmptyFields() {

        if(Util.checkEmptyFields(programId.getText(),programName.getText(),sectionId.getSelectionModel().getSelectedItem(),String.valueOf(programDate.getValue()),programTime.getText(),programDescription.getText())){
            return true;
        }
        return false;
    }

    public void searchProgramField(ActionEvent actionEvent) {
    }
}
