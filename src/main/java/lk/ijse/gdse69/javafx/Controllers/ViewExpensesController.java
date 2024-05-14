package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse69.javafx.Model.Expences;
import lk.ijse.gdse69.javafx.Repository.ExpencesRepo;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;


    public TextField searchId;

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

        setToolTip();
        setSearchIds();
    }

    private void setSearchIds() {
        List<String> expenIds = new ArrayList<>();

        List<Expences> allExpences = ExpencesRepo.getAllExpenses();
        for (Expences expences : allExpences) {
            expenIds.add(expences.getExpenceId()+" - "+expences.getType());
        }
        String[] possibleNames = expenIds.toArray(new String[0]);

        TextFields.bindAutoCompletion(searchId, possibleNames);
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

    public  void searchField(ActionEvent actionEvent) throws IOException {
        String id = this.searchId.getText().split(" - ")[0];
        SearchId.setExpensesId(id);
        createStage("/View/ExpensesSetting.fxml");
    }
}
