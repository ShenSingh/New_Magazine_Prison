package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewExpensesController extends MainDashBoard implements Initializable {
    public AnchorPane MainAnchorPane;

    public TableColumn tvExpensesId;
    public TableColumn tvSectionId;
    public TableColumn tvMonth;
    public TableColumn tvType;
    public TableColumn tvCost;

    public JFXComboBox viewOptionCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        viewOptionCombo.getItems().addAll("All", "This Month");



    }
}
