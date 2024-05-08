package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse69.javafx.Model.Expences;
import lk.ijse.gdse69.javafx.Repository.ExpencesRepo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewExpensesController extends MainDashBoard implements Initializable {
    public AnchorPane MainAnchorPane;

    public TableColumn<Expences, String> tvExpensesId;
    public TableColumn<Expences, String> tvSectionId;
    public TableColumn<Expences, String> tvMonth;
    public TableColumn<Expences, String> tvType;
    public TableColumn<Expences,String> tvCost;

    public JFXComboBox viewOptionCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        viewOptionCombo.getItems().addAll("All", "Food", "Medicine", "Electricity", "Water", "Staff");

        try {
            setTableDate(ExpencesRepo.getAllExpenses());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        viewOptionCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("All")){
                setTableDate(ExpencesRepo.getAllExpenses());
            }
            else if (newValue.equals("Food")){
                setTableDate(ExpencesRepo.getExpensesByType("Food"));
            }
            else if (newValue.equals("Medicine")) {
                setTableDate(ExpencesRepo.getExpensesByType("Medicine"));
            }
            else if (newValue.equals("Female")) {
                setTableDate(ExpencesRepo.getExpensesByType("Electricity"));
            }
            else if (newValue.equals("Water")) {
                setTableDate(ExpencesRepo.getExpensesByType("Water"));
            }
            else if (newValue.equals("Staff")) {
                setTableDate(ExpencesRepo.getExpensesByType("Staff"));
            }
        });
    }

    private void setTableDate(List<Expences> expences) {

        if(expences != null){
            tvExpensesId.setCellValueFactory(new PropertyValueFactory<>("expenceId"));
            tvSectionId.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
            tvMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
            tvType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tvCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

            tvExpensesId.getTableView().setItems(FXCollections.observableArrayList(expences));
        }
    }
}
