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
    public TextField sectionId;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            totalSectionCount.setText(String.valueOf(SectionRepo.getAllSections().size())+" Sections");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setToolTip();
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
        sectionId.clear();
        programDate.setValue(null);
        programTime.clear();
        programDescription.clear();
    }

    public void submitBtn(ActionEvent actionEvent) throws SQLException {

        if (checkEmptyFields()) {



            if (checkSectionId()){}else {return;}
            if (programIdValidation()){}else {return;}
            if (checkProgramId()){}else {return;}
            if (checkTimeValidation()){}else {return;}

            String programId = this.programId.getText();
            String programName = this.programName.getText();
            String sectionId = this.sectionId.getText();
            LocalDate programDate = this.programDate.getValue();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
            String time = this.programTime.getText().trim();
            LocalTime programTime = LocalTime.parse(time, formatter);
            String description = programDescription.getText();

            Program program = new Program(programId, programName, sectionId, programDate, programTime, description);

            boolean isAdded = ProgramRepo.save(program);

            if (isAdded) {
                clearFields();
                ShowAlert showAlert = new ShowAlert("Success", "Program Added", "Program Added Successfully", Alert.AlertType.INFORMATION);
            } else {
                clearFields();
                ShowAlert showAlert = new ShowAlert("Error", "Program Not Added", "Program Not Added", Alert.AlertType.ERROR);
            }

        } else {
            ShowAlert showAlert = new ShowAlert("Error", "Empty Fields", "Please Fill All Fields", Alert.AlertType.ERROR);
        }
    }

    private boolean checkTimeValidation() {
        String time = this.programTime.getText().trim();

        if (time.matches("^(?:[01]\\d|2[0-3]).(?:[0-5]\\d)$")){
            return true;
        }
        ShowAlert showAlert = new ShowAlert("Error", "Invalid Program Time", "Invalid Program Time Ex : XX.XX", Alert.AlertType.WARNING);
        return false;
    }

    private boolean programIdValidation() {
        String id = programId.getText().trim();
        if (id.matches("P\\d{3}")) {
            return true;
        } else {
            ShowAlert showAlert = new ShowAlert("Error", "Invalid Program Id", "Invalid Program Id Ex : PXXX", Alert.AlertType.WARNING);
            return false;
        }
    }

    private boolean checkProgramId() {
        List<Program> programs =new ArrayList<>();

        try {
            programs = ProgramRepo.getAllPrograms();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        for (Program program : programs) {
            if (program.getProgramId().equals(programId.getText())) {
                ShowAlert showAlert = new ShowAlert("Error", "Program Id Already Exist", "Program Id Already Exist", Alert.AlertType.WARNING);
                return false;
            }
        }
        return true;
    }

    private boolean checkSectionId() {
        List<Section> sections =new ArrayList<>();

        try {
            sections = SectionRepo.getAllSections();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Section section : sections) {
            if (section.getSectionId().equals(sectionId.getText())) {
                return true;
            }
        }
        ShowAlert showAlert = new ShowAlert("Error", "Section Id Not Found", "Section Id Not Found", Alert.AlertType.ERROR);
        return false;
    }

    private boolean checkEmptyFields() {

        if (programId.getText().isEmpty() || programName.getText().isEmpty() || sectionId.getText().isEmpty() || programDate.getValue() == null || programTime.getText().isEmpty() || programDescription.getText().isEmpty()) {
            System.out.println("Empty Fields");
            return false;
        } else {
            System.out.println("Not Empty Fields");
            return true;
        }
    }
}
