package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Visitor;
import lk.ijse.gdse69.javafx.Repository.VisitorRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewVisitorController extends MainDashBoard implements Initializable {


    public TableColumn<Visitor, String> tvVisitorId;
    public TableColumn<Visitor, String> tvFName;
    public TableColumn<Visitor, String> tvLName;
    public TableColumn<Visitor, String> tvDOB;
    public TableColumn<Visitor, String> tvGender;
    public TableColumn<Visitor, String> tvNIC;
    public TableColumn<Visitor, String> tvAddress;
    public TableColumn<Visitor, String> tvNumber;
    public TableColumn<Visitor, String> tvType;

    @FXML
    private JFXComboBox<String> viewVisitorOption;

    @FXML
    private Text totalVisitorCount;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            String count=String.valueOf(VisitorRepo.getAllVisitors().size());
            totalVisitorCount.setText(count+" Visitors");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            setTableValues(VisitorRepo.getAllVisitors());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        viewVisitorOption.getItems().addAll("All", "Male", "Female");

        viewVisitorOption.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("All")){
                try {
                    setTableValues(VisitorRepo.getAllVisitors());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            else if (newValue.equals("Male")) {
                try {
                    setTableValues(VisitorRepo.getVisitorByGender("Male"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (newValue.equals("Female")) {
                try {
                    setTableValues(VisitorRepo.getVisitorByGender("Female"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setTableValues(List<Visitor> allVisitors) {
        if (allVisitors != null){
            tvVisitorId.setCellValueFactory(new PropertyValueFactory<>("visitorID"));
            tvFName.setCellValueFactory(new PropertyValueFactory<>("visitorFirstName"));
            tvLName.setCellValueFactory(new PropertyValueFactory<>("visitorLastName"));
            tvDOB.setCellValueFactory(new PropertyValueFactory<>("visitorDOB"));
            tvNIC.setCellValueFactory(new PropertyValueFactory<>("visitorNIC"));
            tvGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            tvAddress.setCellValueFactory(new PropertyValueFactory<>("visitorAddress"));
            tvNumber.setCellValueFactory(new PropertyValueFactory<>("visitorNumber"));
            tvType.setCellValueFactory(new PropertyValueFactory<>("visitorType"));

            tvAddress.getTableView().setItems(FXCollections.observableArrayList(allVisitors));
        }
    }


    public void submitRecordBtn(ActionEvent actionEvent) {
    }

    public void scanQrBtn(ActionEvent actionEvent) {
    }

    public void searchInmateIdBtn(ActionEvent actionEvent) {
    }
}
