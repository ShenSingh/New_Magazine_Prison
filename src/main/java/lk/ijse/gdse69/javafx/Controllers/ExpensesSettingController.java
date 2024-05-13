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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExpensesSettingController extends MainDashBoard implements Initializable {


    public AnchorPane MainAnchorPane;

    public TextField expensesId;
    public TextField sectionId;
    public TextField cost;
    public TextField month;
    public ComboBox<String> type;


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
        type.getItems().addAll("Food","Water","Staff","Electric","Equipment","Health");
        setToolTip();
        expensesId.setEditable(false);
        sectionId.setEditable(false);

        setSearchFieldIds();

        nonPageSearchId();
    }

    private void nonPageSearchId() {
        String id = AddExpensesController.getExpenId();

        if (id != null){
            setSearchValues(id);
        }

    }

    private void setSearchFieldIds() {
        List<String> expenIds = new ArrayList<>();

        List<Expences> allExpens = ExpencesRepo.getAllExpenses();
        for (Expences expences : allExpens) {
            expenIds.add(expences.getExpenceId()+" - "+expences.getType());
        }
        String[] possibleNames = expenIds.toArray(new String[0]);

        TextFields.bindAutoCompletion(searchField, possibleNames);

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
    public void saveBtn(ActionEvent actionEvent) {

        if (createSetId(searchField.getText()).isEmpty()){
            ShowAlert.showErrorNotify("Empty Search Field. Please Search Id");
            return;
        }

        if (checkEmptyFields()){

            String id = expensesId.getText();
            String secId = sectionId.getText();
            String type = this.type.getSelectionModel().getSelectedItem();
            double cost = Double.parseDouble(this.cost.getText());
            String month = this.month.getText();

            Expences expenses = new Expences(id,secId,month,type,cost);

            try {
                boolean isUpdated = ExpencesRepo.update(expenses);
                if(isUpdated){
                    ShowAlert.showSuccessNotify("Expenses Updated Successfully");
                }else{
                    ShowAlert.showErrorNotify("Expenses Not Updated");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            ShowAlert.showErrorNotify("Please Fill All Fields");
        }

    }

    public boolean checkEmptyFields(){
        return Util.checkEmptyFields(expensesId.getText(),sectionId.getText(),cost.getText(),month.getText(),type.getSelectionModel().getSelectedItem());
    }

    public void cancelBtn(ActionEvent actionEvent) {
        clearFields();
    }


    public void seachExpenses(ActionEvent actionEvent) {
        String id = searchField.getText();
        setSearchValues(id);
    }

    private void setSearchValues(String id) {
        String idS =  createSetId(id);

        if (idS.isEmpty()){
            ShowAlert.showErrorNotify("Empty Search Field. Please Search Id");
            return;
        }
        try {
            Expences expences = ExpencesRepo.search(idS);
            if (expences != null){
                setSectionDetails(expences.getSectionId());
                expensesId.setText(expences.getExpenceId());
                sectionId.setText(expences.getSectionId());
                cost.setText(String.valueOf(expences.getCost()));
                month.setText(expences.getMonth());
                type.getSelectionModel().select(expences.getType());
            }else{
                ShowAlert.showErrorNotify("Expenses Not Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSectionDetails(String sectionId) {
        try {
            Section section = SectionRepo.search(sectionId);
            if (section != null){
                OPsectionId.setText(section.getSectionId());
                OPsectionName.setText(section.getSectionName());
                OPlocation.setText(section.getLocation());
                OPcapacity.setText(String.valueOf(section.getCapacity()));
                OPsecurityLevel.setText(section.getSecurityLevel());
                OPstatus.setText(section.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteExpenses(ActionEvent actionEvent) {
        String id = searchField.getText();

        String idS = createSetId(id);

        if (idS.isEmpty()){
            ShowAlert.showErrorNotify("Empty Search Field. Please Search Id");
            return;
        }
        try {
            boolean isDeleted = ExpencesRepo.delete(idS);
            if (isDeleted){
                ShowAlert.showSuccessNotify("Expenses Deleted Successfully");
                clearFields();
            }else{
                ShowAlert.showErrorNotify("Expenses Not Deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createSetId(String id) {
        int index = id.indexOf(" ");
        String idS = id.substring(0, index); // Extract the substring before the first space
        System.out.println(idS);

        return idS;
    }
    public void editProfileTogal(ActionEvent actionEvent) {
        isEdit = !isEdit;

        cost.setEditable(isEdit);
        month.setEditable(isEdit);
        type.setDisable(!isEdit);
    }

    private void clearFields() {
        expensesId.clear();
        sectionId.clear();
        cost.clear();
        month.clear();
        type.getSelectionModel().clearSelection();
    }
}
