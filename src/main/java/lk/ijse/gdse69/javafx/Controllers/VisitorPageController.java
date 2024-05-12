package lk.ijse.gdse69.javafx.Controllers;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.FlogQRCode.QRCodeScanner;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.Visitor;
import lk.ijse.gdse69.javafx.Model.VisitorRecord;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.VisitorRecordRepo;
import lk.ijse.gdse69.javafx.Repository.VisitorRepo;
import lk.ijse.gdse69.javafx.Util.Util;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    
    public ComboBox<String> visitorIdCombo;
    public TextField visitorIdFieldTxt;

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
    @FXML
    private TextField demoId;

    @FXML
    JComboBox<String> visitorIdComboBox = new JComboBox<>();

    public static String flogVisitorId;

    @FXML
    JTextField visitorIdField;

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

        System.out.println("Visitor Page");

        tvAddress.getTableView().setStyle(
                "-fx-table-view-column-border: transparent; " + // Optional: Hides the column borders
                        ".tableView .column-header-background {" +
                        "    -fx-background-color: transparent;" +
                        "}" +
                        ".tableView .column-header {" +
                        "    -fx-text-fill: white;" +
                        "}" // Add a closing brace here
        );

        try {
            setTableDate(VisitorRepo.getAllVisitors());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setVisitorCounts();
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

    private void setVisitorCounts() {
        try {
            totalVisitorCount.setText(String.valueOf(VisitorRepo.getAllVisitors().size())+" Visitors");
            familyCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Immediate Family").size())+" Visitors");
            legalRepresentativesCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Legal Representative").size())+" Visitors");
            visitOfficerCount.setText(String.valueOf(VisitorRepo.getVisitorByVisitorType("Officials").size())+" Visitors");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTableDate(List<Visitor> allVisitors) {

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

        if (recordCheckEmptyFields()){
            if (Util.checkValidText(visitorRecordId.getText(), "VR\\d{3}")){

                Date newDate = Date.valueOf(LocalDate.now());

                LocalTime localTime = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedTime = localTime.format(formatter);
                Time newTime = Time.valueOf(formattedTime);

                VisitorRecord visitorRecord = new VisitorRecord(visitorRecordId.getText(),visitorId.getText(),inmateId.getText(),newDate,newTime);

                try {
                    boolean isAdded = VisitorRecordRepo.save(visitorRecord);

                    if (isAdded){
                        new ShowAlert("Success","Record Added","Record Added Successfully", Alert.AlertType.INFORMATION);
                    }else {
                        new ShowAlert("Error","Record Not Added","Record Not Added", Alert.AlertType.ERROR);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }else {
            new ShowAlert("Error","Empty Fields","Please Fill All Fields", Alert.AlertType.ERROR);
        }
    }

    private boolean recordCheckEmptyFields() {

        if (Util.checkEmptyFields(visitorId.getText(),inmateId.getText(),visitorRecordId.getText())){
            return true;
        }
        return false;
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
                ShowAlert alert=new ShowAlert("Error","Visitor Not Found","Visitor Not Found", Alert.AlertType.ERROR);
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
                ShowAlert alert=new ShowAlert("Error","Inmate Not Found","Inmate Not Found", Alert.AlertType.ERROR);
            }
        }else{
            System.out.println("inmateId Field Empty");
            ShowAlert alert=new ShowAlert("Error","Empty Field","Please Fill Inmate Id Field", Alert.AlertType.ERROR);
        }
    }

    public void setIdsReleased(KeyEvent keyEvent) throws SQLException {
        if (keyEvent.getSource() == visitorIdFieldTxt){
            String input = visitorIdFieldTxt.getText();
            // Your logic here for text field
            System.out.println("Key Released in Text Field: " + input);

            List<Visitor> visitors = VisitorRepo.getVisitorsByInput(input);

            for (int i = 0; i < visitors.size(); i++) {
                demoId.setPromptText(visitors.get(i).getVisitorFirstName()+" "+visitors.get(i).getVisitorLastName());
                System.out.println("Visitor ID : "+visitors.get(i).getVisitorID());
                setTableDate(visitors.get(i));
            }

        }
    }
    private void setTableDate(Visitor visitor) {

        List<Visitor> demoVisitor = new ArrayList<>();
        demoVisitor.add(visitor);

        if (visitor != null){
            tvVisitorId.setCellValueFactory(new PropertyValueFactory<>("visitorID"));
            tvFName.setCellValueFactory(new PropertyValueFactory<>("visitorFirstName"));
            tvLName.setCellValueFactory(new PropertyValueFactory<>("visitorLastName"));
            tvDOB.setCellValueFactory(new PropertyValueFactory<>("visitorDOB"));
            tvNIC.setCellValueFactory(new PropertyValueFactory<>("visitorNIC"));
            tvGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            tvAddress.setCellValueFactory(new PropertyValueFactory<>("visitorAddress"));
            tvNumber.setCellValueFactory(new PropertyValueFactory<>("visitorNumber"));
            tvType.setCellValueFactory(new PropertyValueFactory<>("visitorType"));

            tvAddress.getTableView().setItems(FXCollections.observableArrayList(demoVisitor));
        }
    }

    public void searchVisitorVPBtn(ActionEvent actionEvent) throws IOException {

        if (Util.checkValidText(this.visitorIdFieldTxt.getText(),"V\\d{3}")){
            this.flogVisitorId=visitorIdFieldTxt.getText();
            createStage("/View/VisitorProfile.fxml");
        }
    }
    public static String getFlogVisitorId(){return flogVisitorId;}
}
