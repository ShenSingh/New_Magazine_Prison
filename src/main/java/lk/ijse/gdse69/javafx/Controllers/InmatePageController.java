package lk.ijse.gdse69.javafx.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class InmatePageController extends MainDashBoard implements Initializable {
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

    public Text activeInmateCount;
    public Text totalInmateCount;
    public Text tGenderInmateCount;
    public Text femaleInmateCount;
    public Text maleInmateCount;

    public PieChart freeSpase;


    ViewInmateController viewInmateController = new ViewInmateController();


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

        setCellValueFactory();
        try {
            setValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            setGenderCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private void setValues() throws SQLException {
        List<Section> allSections = SectionRepo.getJailSections();

        int totalSpase=0;

        for (Section section : allSections) {
            totalSpase+=section.getCapacity();
        }

        int totalInmates = InmateRepo.getAllInmates().size();

        int freeSpaseCount = totalSpase-totalInmates;

        this.freeSpase.getData().add(new PieChart.Data("Free Spase",freeSpaseCount));
        this.freeSpase.getData().add(new PieChart.Data("Occupied Spase",totalInmates));



    }

    private void setGenderCount() throws SQLException {

        maleInmateCount.setText(String.valueOf(InmateRepo.getInmatesByGender("Male").size())+" Inmates");
        femaleInmateCount.setText(String.valueOf(InmateRepo.getInmatesByGender("Female").size())+" Inmates");
        tGenderInmateCount.setText(String.valueOf(InmateRepo.getInmatesByGender("Transgender").size())+" Inmates");
        activeInmateCount.setText(String.valueOf(InmateRepo.getActiveInmates().size())+" Inmates");
        totalInmateCount.setText(String.valueOf(InmateRepo.getAllInmates().size())+" Inmates");

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
