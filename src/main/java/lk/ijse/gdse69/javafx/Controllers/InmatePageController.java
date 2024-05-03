package lk.ijse.gdse69.javafx.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;

import javax.swing.text.TableView;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public class InmatePageController extends MainDashBoard{
    public ImageView sirLankaLogo;
    public AnchorPane MainAnchorPane;

    public TableColumn<Inmate, String> tvInmateId;
    public TableColumn<Inmate, String> tvFName;
    public TableColumn<Inmate, String> tvLName;
    public TableColumn<Inmate, Date> tvDOB;
    public TableColumn<Inmate, String> tvNIC;
    public TableColumn<Inmate, String> tvGender;
    public TableColumn<Inmate, String> tvAddress;
    public TableColumn<Inmate, String> tvStatus;

    public Text totalInmateCount;
    public ProgressIndicator freeSpase;
    public Text maleInmateCount;
    public Text femaleInmateCount;
    public Text athorInmateCount;


    @FXML
    private TableView tableView;
    @FXML
    private Button addInmate;
    @FXML
    private Button deleteInmate;

    @FXML
    private Button updateInmate;

    ViewInmateController viewInmateController = new ViewInmateController();

    public void initialize(){
        setCellValueFactory();
        setValues();
        setGenderCount();
    }

    private void setValues() {
        freeSpase=new ProgressIndicator();
        double progressValue =viewInmateController.setProgressValue();
        //freeSpase.setProgress(progressValue);
    }
    private void setGenderCount() {
        int totalCount= 0;
        int maleCount= 0;
        int femaleCount= 0;
        int athorInmateCount= 0;
        try {
            List<Inmate>  inmates =InmateRepo.getAllInmates();

            totalCount = inmates.size();
            for (Inmate inmate : inmates) {
                if (inmate.getGender().equals("Male")){
                    maleCount++;
                }
                else if(inmate.getGender().equals("Female")){
                    femaleCount++;
                }
                else{
                    athorInmateCount++;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        this.totalInmateCount.setText(String.valueOf(totalCount));
//        this.maleInmateCount.setText(String.valueOf(maleCount));
//        this.femaleInmateCount.setText(String.valueOf(femaleCount));
//        this.athorInmateCount.setText(String.valueOf(athorInmateCount));



    }

    private void setCellValueFactory() {
        tvInmateId.setCellValueFactory(new PropertyValueFactory<>("inmateId"));
        tvFName.setCellValueFactory(new PropertyValueFactory<>("inmateFirstName"));
        tvLName.setCellValueFactory(new PropertyValueFactory<>("inmateLastName"));
        tvDOB.setCellValueFactory(new PropertyValueFactory<>("inmateDOB"));
        tvNIC.setCellValueFactory(new PropertyValueFactory<>("inmateNIC"));
        tvGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tvAddress.setCellValueFactory(new PropertyValueFactory<>("inmateAddress"));
        tvStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            List<Inmate> inmates = InmateRepo.getLastUpdatedInmates();
            tvAddress.getTableView().setItems(FXCollections.observableArrayList(inmates));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void activeInmateBtn(ActionEvent actionEvent) throws IOException {
        List<Inmate> inmates = InmateRepo.getActiveInmates();
        tvAddress.getTableView().setItems(FXCollections.observableArrayList(inmates));


    }

    public void activeCaseBtn(ActionEvent actionEvent) {
    }

    public void releseSoonBtn(ActionEvent actionEvent) {
    }
}
