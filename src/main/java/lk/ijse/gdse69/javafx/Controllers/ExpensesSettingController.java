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

        if (searchField.getText().isEmpty()){
            new ShowAlert("Error", "Empty Search Field", "Please Search Id ", Alert.AlertType.ERROR);
            return;
        }

        if (checkEmptyFields()){
            if (Util.checkValidText(sectionId.getText(),"S\\d{3}")){} else{
                new ShowAlert("Error", "Invalid Section ID", "Section ID should be like SXXX", Alert.AlertType.WARNING);
                return;
            }
            if(Util.checkValidText(expensesId.getText(),"E\\d{3}")){}else{
                new ShowAlert("Error", "Invalid Expenses ID", "Expenses ID should be like EXXX", Alert.AlertType.WARNING);
                return;
            }
            if(checkSectionId()){}else{return;}

            String id = expensesId.getText();
            String secId = sectionId.getText();
            String type = this.type.getSelectionModel().getSelectedItem();
            double cost = Double.parseDouble(this.cost.getText());
            String month = this.month.getText();

            Expences expenses = new Expences(id,secId,month,type,cost);

            try {
                boolean isUpdated = ExpencesRepo.update(expenses);
                if(isUpdated){
                    new ShowAlert("Success", "Expenses Updated", "Expenses Update Successfully", Alert.AlertType.INFORMATION);
                }else{
                    new ShowAlert("Error", "Expenses Not Updated", "Expenses Not Updated", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            new ShowAlert("Error", "Empty Fields", "Please fill all the fields", Alert.AlertType.ERROR);
        }

    }

    private boolean checkExpensesId() {
        List<Expences> expenses = new ArrayList<>();

        try {
            expenses = ExpencesRepo.getAllExpenses();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Expences expence : expenses) {
            if(expence.getExpenceId().equals(expensesId.getText()) ){
                ShowAlert showAlert = new ShowAlert("Error", "Expenses ID Already Exist", "Expenses ID Already Exist", Alert.AlertType.WARNING);
                return false;
            }
        }
        return true;
    }

    private boolean checkSectionId() {
        List<Section> sections = new ArrayList<>();
        try {
            sections = SectionRepo.getAllSections();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Section section : sections) {
            if(section.getSectionId().equals(sectionId.getText())){
                return true;
            }
        }
        ShowAlert showAlert = new ShowAlert("Error", "Invalid Section ID", "Section ID not found", Alert.AlertType.WARNING);
        return false;
    }

    public boolean checkEmptyFields(){
        return Util.checkEmptyFields(expensesId.getText(),sectionId.getText(),cost.getText(),month.getText(),type.getSelectionModel().getSelectedItem());
    }

    public void cancelBtn(ActionEvent actionEvent) {
        clearFields();
    }


    public void seachExpenses(ActionEvent actionEvent) {
        String id = searchField.getText();
        if (id.isEmpty()){
            new ShowAlert("Error", "Empty Search Field", "Please Search Id ", Alert.AlertType.WARNING);
            return;
        }
        try {
            Expences expences = ExpencesRepo.search(id);
            if (expences != null){
                setSectionDetails(expences.getSectionId());
                expensesId.setText(expences.getExpenceId());
                sectionId.setText(expences.getSectionId());
                cost.setText(String.valueOf(expences.getCost()));
                month.setText(expences.getMonth());
                type.getSelectionModel().select(expences.getType());
            }else{
                new ShowAlert("Error", "Expenses Not Found", "Expenses Not Found", Alert.AlertType.ERROR);
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
        if (id.isEmpty()){
            new ShowAlert("Error", "Empty Search Field", "Please Search Id ", Alert.AlertType.WARNING);
            return;
        }
        try {
            boolean isDeleted = ExpencesRepo.delete(id);
            if (isDeleted){
                new ShowAlert("Success", "Expenses Deleted", "Expenses Deleted Successfully", Alert.AlertType.INFORMATION);
                clearFields();
            }else{
                new ShowAlert("Error", "Expenses Not Deleted", "Expenses Not Deleted", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editProfileTogal(ActionEvent actionEvent) {
        isEdit = !isEdit;

        expensesId.setEditable(isEdit);
        sectionId.setEditable(isEdit);
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
