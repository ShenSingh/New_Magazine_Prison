package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Expences;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.ExpencesRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import lk.ijse.gdse69.javafx.Util.Util;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddExpensesController extends MainDashBoard implements Initializable {
    public TextField expensesId;
    public ComboBox<String> sectionId;
    public ComboBox<String> expensesType;
    public TextField cost;

    public Text lyFoodExpen;
    public Text lyWaterExpen;
    public Text lyStaffExpen;
    public Text lyElectricExpen;
    public Text lyEquipmentExpen;
    public Text iyHealthExpen;
    public AnchorPane MainAnchorPane;

    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;


    public TextField shearchExpenId;

    public static String expenId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensesType.getItems().addAll("Food","Water","Staff","Electric","Equipment","Health");
        setToolTip();
        setNextExpenId();
        setComboValues();

        setSearchExpenId();
    }

    private void setSearchExpenId() {
        List<String> expenIds = new ArrayList<>();

        List<Expences> allExpences = ExpencesRepo.getAllExpenses();
        for (Expences expences : allExpences) {
            expenIds.add(expences.getExpenceId()+" - "+expences.getType());
        }
        String[] possibleNames = expenIds.toArray(new String[0]);

        TextFields.bindAutoCompletion(shearchExpenId, possibleNames);
    }

    private void setNextExpenId() {
        try {
            String lastId = ExpencesRepo.getLastId();
            if(lastId == null){
                expensesId.setText("E001");
            }else{
                int id = Integer.parseInt(lastId.substring(1));
                id++;
                if(id<10){
                    expensesId.setText("E00"+id);
                }else if(id<100){
                    expensesId.setText("E0"+id);
                }else{
                    expensesId.setText("E"+id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        expensesId.setEditable(false);
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
        expensesId.clear();
        sectionId.getSelectionModel().clearSelection();
        expensesType.getSelectionModel().clearSelection();
        cost.clear();
        setNextExpenId();
    }

    public void submitBtn(ActionEvent actionEvent) {

        if (checkEmptyFields()){

            String id = expensesId.getText();
            String secId = sectionId.getSelectionModel().getSelectedItem();
            String type = expensesType.getSelectionModel().getSelectedItem();
            double cost = Double.parseDouble(this.cost.getText());

            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue();
            String fullMonth = year+"/"+month;

            Expences expences = new Expences(id,secId,fullMonth,type,cost);

            try {
                boolean isAdded = ExpencesRepo.save(expences);
                if(isAdded){
                    clearFields();
                    ShowAlert.showSuccessNotify("Expenses Added Successfully");

                }else{
                    ShowAlert.showErrorNotify("Failed to Add Expenses");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            ShowAlert.showErrorNotify("Please Fill All Fields");
        }
    }

    private void setComboValues() {
        try {
            List<Section> allSections = SectionRepo.getAllSections();
            List<String> sectionIds = new ArrayList<>();
            for (Section section : allSections) {
                sectionIds.add(section.getSectionId());
            }
            sectionId.getItems().addAll(sectionIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean checkEmptyFields(){

        return Util.checkEmptyFields(expensesId.getText(),sectionId.getSelectionModel().getSelectedItem(),expensesType.getSelectionModel().getSelectedItem(),cost.getText());
    }


    public void searchExpenField(ActionEvent actionEvent) throws IOException {
        String id = shearchExpenId.getText();

        if(id == null){
            ShowAlert.showErrorNotify("Please Enter Expenses ID");
        }else{
            createStage("/View/ExpensesSetting.fxml");
        }
    }

    public String getExpenId(){
        return expenId;
    }
}
