package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.SectionRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewInmateController extends MainDashBoard implements Initializable {


    public AnchorPane MainAnchorPane;
    public JFXComboBox<String> viewOptionCombo;
    public Text totalInmateCount;
    public Text maleInmateCount;
    public Text femaleInmateCount;
    public Text athorInmateCount;

    public TableColumn<Inmate, String> tvInmateId;
    public TableColumn<Inmate, String> tvFName;
    public TableColumn<Inmate, String> tvLName;
    public TableColumn<Inmate, String> tvDOB;
    public TableColumn<Inmate, String> tvNIC;
    public TableColumn<Inmate, String> tvGender;
    public TableColumn<Inmate, String> tvAddress;
    public TableColumn<Inmate, String> tvStatus;

    public ProgressIndicator freeSpaseCycle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewOptionCombo.setItems(FXCollections.observableArrayList("All", "City", "Male","Female"));

        setProgressValue();

        try {
            setTableDate(InmateRepo.getAllInmates());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setGenderCount();

        viewOptionCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("All")){
                try {
                    setTableDate(InmateRepo.getAllInmates());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (newValue.equals("City")){

            }
            else if (newValue.equals("Male")) {
                setTableDate("Male");
            }
            else if (newValue.equals("Female")) {
                setTableDate("Female");
            }
        });
    }

    public double setProgressValue() {
        freeSpaseCycle=new ProgressIndicator();
        List<Section> jailSections = new ArrayList<>();
        int totalCapacity = 0;
        int totalInmates = 0;

        try {
            jailSections = SectionRepo.getJailSections();
            for (Section jailSection : jailSections) {
                // Check for null capacity and handle appropriately
                if (jailSection.getCapacity() != null) {
                    totalCapacity += jailSection.getCapacity();
                }else {
                    System.out.println("===========Capacity is null");
                }
            }
            System.out.println("Total Capacity======: " + totalCapacity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            totalInmates = InmateRepo.getAllInmates().size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Avoid division by zero
        double percentageUsed = totalCapacity != 0 ? ((double) totalInmates / totalCapacity) * 100 : 0.0;
        System.out.println("Percentage Used======: " + percentageUsed);
        freeSpaseCycle.setProgress(percentageUsed);
        freeSpaseCycle.setPrefSize(100, 100);

        return percentageUsed;
    }


    private void setTableDate(String InGender) {

        try {
            setTableDate(InmateRepo.getInmatesByGender(InGender));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTableDate(List<Inmate> inmates) {
        tvInmateId.setCellValueFactory(new PropertyValueFactory<>("inmateId"));
        tvFName.setCellValueFactory(new PropertyValueFactory<>("inmateFirstName"));
        tvLName.setCellValueFactory(new PropertyValueFactory<>("inmateLastName"));
        tvDOB.setCellValueFactory(new PropertyValueFactory<>("inmateDOB"));
        tvNIC.setCellValueFactory(new PropertyValueFactory<>("inmateNIC"));
        tvGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tvAddress.setCellValueFactory(new PropertyValueFactory<>("inmateAddress"));
        tvStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tvAddress.getTableView().setItems(FXCollections.observableArrayList(inmates));
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
        this.totalInmateCount.setText(String.valueOf(totalCount)+" Inmates");
        this.maleInmateCount.setText(String.valueOf(maleCount)+" Inmates");
        this.femaleInmateCount.setText(String.valueOf(femaleCount)+" Inmates");
        this.athorInmateCount.setText(String.valueOf(athorInmateCount)+" Inmates");



    }
}
