package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.Model.Expences;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.ExpencesRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;
import lk.ijse.gdse69.javafx.Util.Util;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddExpensesController extends MainDashBoard implements Initializable {
    public TextField expensesId;
    public TextField sectionId;
    public ComboBox<String> expensesType;
    public TextField cost;

    public Text lyFoodExpen;
    public Text lyWaterExpen;
    public Text lyStaffExpen;
    public Text lyElectricExpen;
    public Text lyEquipmentExpen;
    public Text iyHealthExpen;
    public AnchorPane MainAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensesType.getItems().addAll("Food","Water","Staff","Electric","Equipment","Health");
    }
    public void canselBtn(ActionEvent actionEvent) {
    }

    public void submitBtn(ActionEvent actionEvent) {

        if (checkEmptyFields()){

            if (checkValidExpensesId()){} else{return;}

            if (checkExpensesId()){}else{return;}

            if(checkSectionId()){}else{return;}

            String id = expensesId.getText();
            String secId = sectionId.getText();
            String type = expensesType.getSelectionModel().getSelectedItem();
            double cost = Double.parseDouble(this.cost.getText());

            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue();
            String fullMonth = year+"/"+month;

            Expences expences = new Expences(id,secId,fullMonth,type,cost);

            try {
                boolean isAdded = ExpencesRepo.save(expences);
                if(isAdded){
                    ShowAlert showAlert = new ShowAlert("Success", "Expenses Added", "Expenses Added Successfully", Type.INFORMATIONAL);
                }else{
                    ShowAlert showAlert = new ShowAlert("Error", "Expenses Not Added", "Expenses Not Added", Type.ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            ShowAlert showAlert = new ShowAlert("Error", "Empty Fields", "Please fill all the fields", Type.INFORMATIONAL);
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
            if(expence.getExpenceId().equals(expensesId.getText())){
                ShowAlert showAlert = new ShowAlert("Error", "Expenses ID Already Exist", "Expenses ID Already Exist", Type.ERROR);
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
        ShowAlert showAlert = new ShowAlert("Error", "Invalid Section ID", "Section ID not found", Type.ERROR);
        return false;
    }


    private boolean checkValidExpensesId() {
        String id = expensesId.getText();

        if(id.matches("E\\d{3}")){
            return true;
        }
        ShowAlert showAlert = new ShowAlert("Error", "Invalid Expenses ID", "Expenses ID should be like EXXX", Type.ERROR);
        return false;
    }

    public boolean checkEmptyFields(){

        return Util.checkEmptyFields(expensesId.getText(),sectionId.getText(),expensesType.getSelectionModel().getSelectedItem(),cost.getText());
    }

}
