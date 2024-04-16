package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class AddInmateController extends MainDashBoard {

    @FXML
    private ComboBox<String> caseStatusComboBox;

    public void initialize() {
        System.out.println("Add Inmate Page initialized");

        // Initialize the ComboBox with options
        caseStatusComboBox.getItems().addAll("Low", "Medium", "High");
    }

    @FXML
    public void caseStatusField(ActionEvent actionEvent) {
        // Get the selected item from the ComboBox
        String selectedOption = caseStatusComboBox.getSelectionModel().getSelectedItem();

        if (selectedOption != null) {
            System.out.println("Selected case status: " + selectedOption);

            // You can perform additional actions based on the selected case status
            // For example, update other UI elements or save the selected value
        }
    }

    @FXML
    public void onAddInmateBtn(ActionEvent actionEvent) throws IOException {
        System.out.println("Add Inmate Button Clicked");
        createStage("/View/AddInmate.fxml");
    }
}
