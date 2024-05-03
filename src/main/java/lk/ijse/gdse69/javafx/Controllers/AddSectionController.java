package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSectionController extends MainDashBoard implements Initializable {

    public AnchorPane MainAnchorPane;
    @FXML
    private TextField sectionId;
    @FXML
    private TextField sectionName;
    @FXML
    private TextField location;
    @FXML
    private TextField capacity;
    @FXML
    private ComboBox<String> securityLevelCombo;
    @FXML
    private ComboBox<String> statusCombo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        securityLevelCombo.getItems().addAll("Low", "Medium", "High");
        statusCombo.getItems().addAll("Active", "Inactive");

    }

    public void canselBtn(ActionEvent actionEvent) {
        clearField();
    }

    public void securityLevelComboBox(ActionEvent actionEvent) {
    }

    public void statusComboBox(ActionEvent actionEvent) {
    }



    public void submitBtn(ActionEvent actionEvent) throws SQLException {

        if (checkEmpty()) {
            String sectionId = this.sectionId.getText();
            String sectionName = this.sectionName.getText();
            String location = this.location.getText();
            String capacity = this.capacity.getText();
            String securityLevel = securityLevelCombo.getSelectionModel().getSelectedItem();
            String status = statusCombo.getSelectionModel().getSelectedItem();

            Section section = new Section(sectionId, sectionName, location, Integer.parseInt(capacity), securityLevel, status);

            System.out.println(section);

            if (SectionRepo.save(section)) {
                System.out.println("Section added successfully");
                ShowAlert showAlert = new ShowAlert("Success", "Section Added", "Section added successfully", Type.SUCCESS);
                clearField();
            } else {
                System.out.println("Failed to add section");
                ShowAlert showAlert = new ShowAlert("Error", "Failed to add section", "Failed to add section", Type.ERROR);
                clearField();
            }

        } else {
            System.out.println("Empty fields found");
            ShowAlert showAlert = new ShowAlert("Error", "Empty Fields", "Please fill all the fields", Type.ERROR);
        }
    }

    private boolean checkEmpty() {

        if (sectionId.getText().isEmpty() || sectionName.getText().isEmpty() || location.getText().isEmpty() || capacity.getText().isEmpty() || securityLevelCombo.getSelectionModel().isEmpty() || statusCombo.getSelectionModel().isEmpty()){

            return false;
        }
        return true;
    }

    private void clearField(){
        sectionId.clear();
        sectionName.clear();
        location.clear();
        capacity.clear();
        securityLevelCombo.getSelectionModel().clearSelection();
        statusCombo.getSelectionModel().clearSelection();
    }

}
