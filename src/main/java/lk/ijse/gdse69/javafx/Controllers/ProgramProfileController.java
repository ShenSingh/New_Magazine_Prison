package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Program;
import lk.ijse.gdse69.javafx.Repository.ProgramRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ProgramProfileController extends MainDashBoard implements Initializable {
    public AnchorPane MainAnchorPane;

    public TextField programId;
    public TextField programName;
    public TextField sectionId;
    public TextField programTime;
    public DatePicker programDate;
    public TextField description;

    public Text fullProgramName;

    public TextField searchField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveBtn(ActionEvent actionEvent) throws SQLException {
        String id =searchField.getText();

        if (!id.isEmpty()){

            if (checkEmptyFields()) {

                if (SectionRepo.search(sectionId.getText()) != null) {
                    //search program
                    String programId = this.programId.getText();
                    String programName = this.programName.getText();
                    String sectionId = this.sectionId.getText();
                    LocalDate programDate = this.programDate.getValue();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
                    String time = this.programTime.getText();
                    LocalTime programTime = LocalTime.parse(time, formatter);

                    String description = this.description.getText();

                    Program program = new Program(programId, programName, sectionId, programDate, programTime, description);

                    boolean isUpdated = ProgramRepo.update(program);

                    if (isUpdated) {
                        ShowAlert showAlert = new ShowAlert("Success", "Program Updated", "Program Update Successfully", Alert.AlertType.INFORMATION);
                        clearFields();
                    } else {
                        ShowAlert showAlert = new ShowAlert("Error", "Program Not Updated", "Program Not Update Successfully", Alert.AlertType.ERROR);
                    }
                } else {

                    ShowAlert showAlert = new ShowAlert("Error", "Section Not Found", "Section Not Found", Alert.AlertType.ERROR);
                }

            } else {
                ShowAlert showAlert = new ShowAlert("Error", "Empty Fields", "Please Fill All Fields", Alert.AlertType.ERROR);
            }
        }
    }

    public void cancelBtn(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        programId.clear();
        programName.clear();
        sectionId.clear();
        programTime.clear();
        programDate.setValue(null);
        description.clear();
    }

    public void seachProgram(ActionEvent actionEvent) throws SQLException {

        String id =searchField.getText();

        if (!id.isEmpty()){

            Program program = ProgramRepo.search(id);

            if (program != null){
                programId.setText(program.getProgramId());
                programName.setText(program.getProgramName());
                sectionId.setText(program.getSectionId());

                LocalTime programTimes = program.getProgramTime(); // Assuming programTime is a LocalTime object
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
                String formattedTime = programTimes.format(formatter);
                programDate.setValue(program.getProgramDate());
                programTime.setText(formattedTime);

                description.setText(program.getDescription());
            }else {
                ShowAlert showAlert= new ShowAlert("Error","Program Not Found","Program Not Found", Alert.AlertType.ERROR);
            }
        }else {
            ShowAlert showAlert= new ShowAlert("Error","Empty Fields","Please Enter Program Id", Alert.AlertType.ERROR);
        }
    }

    private boolean checkEmptyFields() {
        if (programId.getText().isEmpty() || programName.getText().isEmpty() || sectionId.getText().isEmpty() || programTime.getText().isEmpty() || programDate.getValue() == null || description.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public void editProfileTogal(ActionEvent actionEvent) {
    }

    public void deleteProgram(ActionEvent actionEvent) throws SQLException {
        String id=searchField.getText();

        if (!id.isEmpty()) {
            boolean isDeleted = ProgramRepo.delete(id);

            if (isDeleted) {
                ShowAlert showAlert = new ShowAlert("Success", "Program Deleted", "Program Deleted Successfully", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                ShowAlert showAlert = new ShowAlert("Error", "Program Not Deleted", "Program Not Deleted Successfully", Alert.AlertType.ERROR);
            }
        }
    }


}
