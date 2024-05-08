package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Officer;
import lk.ijse.gdse69.javafx.Repository.OfficerRepo;

import java.sql.SQLException;
import java.util.List;

public class ViewOfficerController extends MainDashBoard{

@FXML
private JFXComboBox<String> viewOptionCombo;

    public ImageView sirLankaLogo;
    public AnchorPane MainAnchorPane;
    public TableColumn<Officer, String> officerId;
    public TableColumn<Officer, String> fName;
    public TableColumn<Officer, String> lName;
    public TableColumn<Officer, String> DOB;
    public TableColumn<Officer, String> NIC;
    public TableColumn<Officer, String> address;
    public TableColumn<Officer, String> email;
    public TableColumn<Officer, String> number;
    public TableColumn<Officer, String> position;
    public TableColumn<Officer, String> secId;
    public TableColumn<Officer, String> gender;
    public TableColumn<Officer, String> salary;


    public Text totalOfficerCount;
    public Text maleOfficerCount;
    public Text femaleOfficerCount;
    public Text specialUnitCount;

    public void initialize(){

        viewOptionCombo.getItems().addAll("View All","Male","Female");
        viewOptionCombo.getSelectionModel().select(0);

        setOfficerCount();
        try {
            setTableData(OfficerRepo.getAllOfficers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getComboBoxOption();



    }

    private void setOfficerCount() {
        List<Officer> allOfficers = null;

        try {
            allOfficers = OfficerRepo.getAllOfficers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (allOfficers != null) {
            String totalCount=String.valueOf(allOfficers.size());
            String totalC=totalCount + " Officers";
            totalOfficerCount.setText(totalC);

            long maleCount = allOfficers.stream().filter(officer -> officer.getGender().equals("Male")).count();
            String totalMale = String.valueOf(maleCount);
            String maleC= totalMale + " Officers";
            maleOfficerCount.setText(maleC);

            long femaleCount = allOfficers.stream().filter(officer -> officer.getGender().equals("Female")).count();
            String totalfemale = String.valueOf(femaleCount);
            String femaleC= totalfemale + " Officers";
            femaleOfficerCount.setText(femaleC);

            specialUnitCount.setText(seUniCount(allOfficers));

        }
    }
    private String seUniCount(List<Officer> allOfficers) {
        long specialUnitCount = allOfficers.stream().filter(officer -> officer.getPosition().equals("Special Unit")).count();
        String specUniCount= String.valueOf(specialUnitCount);
        String count= specUniCount + " Officers";
        return count;
    }

    private void getComboBoxOption() {
        viewOptionCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("All")){
                try {
                    setTableData(OfficerRepo.getAllOfficers());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (newValue.equals("Male")) {
                try {
                    setTableData(OfficerRepo.getOfficerByGender("Male"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (newValue.equals("Female")) {
                try {
                    setTableData(OfficerRepo.getOfficerByGender("Female"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



    private void setTableData(List<Officer> officers){
        if (officers != null) {
            officerId.setCellValueFactory(new PropertyValueFactory<>("officerId"));
            fName.setCellValueFactory(new PropertyValueFactory<>("officerFirstName"));
            lName.setCellValueFactory(new PropertyValueFactory<>("officerLastName"));
            DOB.setCellValueFactory(new PropertyValueFactory<>("officerDOB"));
            NIC.setCellValueFactory(new PropertyValueFactory<>("officerNIC"));
            address.setCellValueFactory(new PropertyValueFactory<>("officerAddress"));
            email.setCellValueFactory(new PropertyValueFactory<>("officerEmail"));
            number.setCellValueFactory(new PropertyValueFactory<>("officerNumber"));
            position.setCellValueFactory(new PropertyValueFactory<>("position"));
            secId.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
            gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

            salary.getTableView().setItems(FXCollections.observableArrayList(officers));
        }else {
            System.out.println("null");
        }

    }


}
