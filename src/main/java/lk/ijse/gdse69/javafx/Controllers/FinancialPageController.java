package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Repository.ExpencesRepo;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class FinancialPageController extends MainDashBoard implements Initializable {
    public AnchorPane MainAnchorPane;
    public LineChart expencesLineChart;

    public Text iyHelthExpen;
    public Text lyEquipmentExpen;
    public Text lyElectricExpen;
    public Text lyStaffExpen;
    public Text lyWaterExpen;
    public Text lyFoodExpen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLyValues();
        Map<String, Double> totalCost = ExpencesRepo.getTotalCostByType();

        for (Map.Entry<String, Double> entry : totalCost.entrySet()) {
            System.out.println("Expense Type: " + entry.getKey() + ", Total Cost: " + entry.getValue());
            //
            //
            //
            if (entry.getKey().equals("Health")) {
                iyHelthExpen.setText(String.valueOf(entry.getValue()));
            }else if (entry.getKey().equals("Equipment")){
                lyEquipmentExpen.setText(String.valueOf(entry.getValue()));
            }else if (entry.getKey().equals("Electric")){
                lyElectricExpen.setText(String.valueOf(entry.getValue()));
            }else if (entry.getKey().equals("Staff")){
                lyStaffExpen.setText(String.valueOf(entry.getValue()));
            }else if (entry.getKey().equals("Water")){
                lyWaterExpen.setText(String.valueOf(entry.getValue()));
            }else if (entry.getKey().equals("Food")){
                lyFoodExpen.setText(String.valueOf(entry.getValue()));
            }
        }
    }

    private void setLyValues() {

    }

    public void staffBtn(ActionEvent actionEvent) {
    }

    public void healthBtn(ActionEvent actionEvent) {
    }

    public void equipmentBtn(ActionEvent actionEvent) {
    }

}
