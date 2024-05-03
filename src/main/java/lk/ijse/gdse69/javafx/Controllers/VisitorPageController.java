package lk.ijse.gdse69.javafx.Controllers;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.FlogQRCode.QRCodeScanner;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.Visitor;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.VisitorRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class VisitorPageController extends MainDashBoard implements Initializable {
    public ImageView sirLankaLogo;

    public TableColumn<Visitor, String> tvVisitorId;
    public TableColumn<Visitor, String> tvFName;
    public TableColumn<Visitor, String> tvLName;
    public TableColumn<Visitor, String> tvDOB;
    public TableColumn<Visitor, String> tvNIC;
    public TableColumn<Visitor, String> tvGender;
    public TableColumn<Visitor, String> tvAddress;
    public TableColumn<Visitor, String> tvNumber;
    public TableColumn<Visitor,String> tvType;
    public AnchorPane MainAnchorPane;

    @FXML
    private Text totalVisitorCount;
    @FXML
    private Text familyCount;
    @FXML
    private Text legalRepresentativesCount;
    @FXML
    private Text visitOfficerCount;

    @FXML
    private TextField inmateId;
    @FXML
    private TextField visitorId;
    @FXML
    private TextField visitorNIC;
    @FXML
    private TextField visitorName;
    @FXML
    private TextField inmateName;
    @FXML
    private TextField visitorRecordId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Visitor Page");

        try {
            setTableDate(VisitorRepo.getAllVisitors());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setVisitorCounts();
    }


    private void setVisitorCounts() {
        try {
            totalVisitorCount.setText(String.valueOf(VisitorRepo.getAllVisitors().size()));
            familyCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Immediate Family").size()));
            legalRepresentativesCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Legal Representative").size()));
            visitOfficerCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Officials").size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTableDate(List<Visitor> allVisitors) {


        for(Visitor visitor:allVisitors){
            System.out.println(visitor.getGender());
        }

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


    ////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
    public void familyBtn(ActionEvent actionEvent) throws SQLException {
        setTableDate(VisitorRepo.getVisitorByVisitorType("Immediate Family"));
    }

    public void legalRepBtn(ActionEvent actionEvent) throws SQLException {
        setTableDate(VisitorRepo.getVisitorByVisitorType("Legal Representative"));
    }

    public void visitOffficerBtn(ActionEvent actionEvent) throws SQLException {
        setTableDate(VisitorRepo.getVisitorByVisitorType("Officials"));
    }
    ///////////////////////
    //////////////////////
    public void submitRecordBtn(ActionEvent actionEvent) {
    }


    public void scanQrBtn(ActionEvent actionEvent) throws ChecksumException, FormatException, SQLException {
        String searchVisitorId = QRCodeScanner.qrScanner();

        if (searchVisitorId != null){
            Visitor visitor = VisitorRepo.search(searchVisitorId);

            if (visitor != null){
                visitorId.setText(visitor.getVisitorID());
                visitorName.setText(visitor.getVisitorFirstName() + " " + visitor.getVisitorLastName());
                visitorNIC.setText(visitor.getVisitorNIC());
                visitorId.setEditable(false);
                visitorName.setEditable(false);
                visitorNIC.setEditable(false);
            } else {
                visitorId.clear();
                System.out.println("Visitor Not Found");
                ShowAlert alert=new ShowAlert("Error","Visitor Not Found","Visitor Not Found", Type.ERROR);
            }
        }
    }

    public void searchInmateIdBtn(ActionEvent actionEvent) throws SQLException {
        String newInmateId = inmateId.getText();

        if (newInmateId != null){
            Inmate inmate = InmateRepo.search(newInmateId);

            if (inmate != null){
                inmateName.setText(inmate.getInmateFirstName() + " " + inmate.getInmateLastName());
                inmateName.setEditable(false);
                inmateId.setEditable(false);
            } else {
                inmateId.clear();
                System.out.println("Inmate Not Found");
                ShowAlert alert=new ShowAlert("Error","Inmate Not Found","Inmate Not Found", Type.ERROR);
            }
        }else{
            System.out.println("inmateId Field Empty");
            ShowAlert alert=new ShowAlert("Error","Empty Field","Please Fill Inmate Id Field", Type.ERROR);
        }
    }


}
