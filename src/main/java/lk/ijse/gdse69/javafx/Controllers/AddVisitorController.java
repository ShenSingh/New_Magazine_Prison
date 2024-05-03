package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Alert.Type;
import lk.ijse.gdse69.javafx.FlogQRCode.QRCodeGenerator;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.SetFirstVisitorRecord;
import lk.ijse.gdse69.javafx.Model.Visitor;
import lk.ijse.gdse69.javafx.Model.VisitorRecord;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.SetFirstVisitorRecordRepo;
import lk.ijse.gdse69.javafx.Repository.VisitorRepo;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddVisitorController extends MainDashBoard implements Initializable {


    @FXML
    private TextField visitorId;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private DatePicker DOB;
    @FXML
    private TextField NIC;
    @FXML
    private TextField number;
    @FXML
    private TextField address;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private ComboBox<String> visitorType;

    //////////////////

    @FXML
    private TextField visitorRecordId;
    @FXML
    private TextField inmateId;
    @FXML
    private TextField inmateName;
    @FXML
    private TextField inmateNIC;

    @FXML
    private Text totalVisitorCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            totalVisitorCount.setText(String.valueOf(VisitorRepo.getAllVisitors().size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setValuesComboBox();
    }

    private void setValuesComboBox() {
        gender.getItems().addAll("Male", "Female");
        visitorType.getItems().addAll("Immediate Family", "Legal Representative", "Officials", "Others");
    }

    public void searchInmateIdBtn(ActionEvent actionEvent) throws SQLException {
        String newInmateId = inmateId.getText();

        if (newInmateId != null){
            Inmate inmate = InmateRepo.search(newInmateId);

            if (inmate != null){
                inmateName.setText(inmate.getInmateFirstName() + " " + inmate.getInmateLastName());
                inmateNIC.setText(inmate.getInmateNIC());
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

    public void canselBtn(ActionEvent actionEvent) {
        clearFields();
    }


    public void createQrBtn(ActionEvent actionEvent) {


        if (checkEmptyFields()) {
           //
            String newVisitorId = visitorId.getText();

            String filePath = "src/main/resources/QRCodeStore/"+newVisitorId+".png";

            boolean isGenerated = QRCodeGenerator.generateQRCode(newVisitorId);



            if (isGenerated){
                createVisitorObject();
                showQRCodeDialog(filePath);

            } else {
                System.out.println("Error Generating QR Code");
                ShowAlert alert=new ShowAlert("Error","Error Generating QR Code","Error Generating QR Code", Type.ERROR);
            }

        } else {
            System.out.println("Empty Fields");
            ShowAlert alert=new ShowAlert("Error","Empty Fields","Please Fill All Fields", Type.ERROR);
        }
    }

    public static void showQRCodeDialog(String filePath) {
        Alert qrCodeAlert = new Alert(Alert.AlertType.INFORMATION);
        qrCodeAlert.setTitle("QR Code");
        qrCodeAlert.setHeaderText("Visitor QR Code");

        // Load the image from the file path
        File file = new File(filePath);
        Image qrCodeImage = new Image(file.toURI().toString());

        // Create an ImageView to display the QR code image
        ImageView imageView = new ImageView(qrCodeImage);
        imageView.setFitWidth(300); // Set width of the ImageView
        imageView.setFitHeight(300); // Set height of the ImageView

        // Set the content of the alert dialog to the ImageView
        qrCodeAlert.getDialogPane().setContent(imageView);

        qrCodeAlert.showAndWait();
    }



    public void submitBtn(ActionEvent actionEvent) throws SQLException {
        if (createVisitorObject() != null && checkRecordEmptyFields()) {
            System.out.println("Not Empty");


            createVisitorRecordObject();

            SetFirstVisitorRecord setFirstVisitorRecord = new SetFirstVisitorRecord(createVisitorObject(),createVisitorRecordObject());
            System.out.println("Visitor Record Object Created");


            boolean isSaved =SetFirstVisitorRecordRepo.save(setFirstVisitorRecord);

            if (isSaved){
                System.out.println("Saved successfully");
                ShowAlert alert=new ShowAlert("Success","Saved","Visitor Record Saved", Type.SUCCESS);
                clearFields();
            } else {
                System.out.println("Saved unsuccessfully");
                ShowAlert alert=new ShowAlert("Error","Not Saved","Visitor Record Not Saved", Type.ERROR);
            }

        } else {
            System.out.println("Empty Fields");
            ShowAlert alert=new ShowAlert("Error","Empty Fields","Please Fill All Fields", Type.ERROR);
        }
    }

    private boolean checkRecordEmptyFields() {
        if (visitorRecordId.getText().isEmpty() || inmateId.getText().isEmpty() || inmateName.getText().isEmpty() || inmateNIC.getText().isEmpty()) {
            return false;
        } else {
            System.out.println("Not Empty");
            return true;
        }
    }

    private VisitorRecord createVisitorRecordObject() {
        String newRecordId = visitorRecordId.getText();
        String newVisitorId = visitorId.getText();
        String newInmateId = inmateId.getText();
        Date newDate = Date.valueOf(LocalDate.now());

        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = localTime.format(formatter);
        Time newTime = Time.valueOf(formattedTime);

        VisitorRecord newVisitorRecord = new VisitorRecord(newRecordId,newVisitorId,newInmateId,newDate,newTime);
        return newVisitorRecord;
    }

    private Visitor createVisitorObject() {

        if (checkEmptyFields()){

        String newVisitorId = visitorId.getText();
        String newFName = fName.getText();
        String newLName = lName.getText();
            String dateString = DOB.getEditor().getText();

// Define a DateTimeFormatter for parsing the date string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

// Parse the date string into a LocalDate object
            LocalDate localDate = LocalDate.parse(dateString, formatter);

// Convert LocalDate to java.sql.Date
            Date newDOB = Date.valueOf(localDate);

        String newNIC = NIC.getText();
        Integer newNumber = Integer.valueOf(number.getText());
        String newAddress = address.getText();
        String newGender=gender.getSelectionModel().getSelectedItem();
        String newVisitorType=visitorType.getSelectionModel().getSelectedItem();

        Visitor newVisitor = new Visitor(newVisitorId,newFName,newLName,newDOB,newNIC,newNumber,newAddress,newVisitorType,newGender);
        return newVisitor;

        }else{
            System.out.println("Empty Fields");
            ShowAlert alert=new ShowAlert("Error","Empty Fields","Please Fill All Fields", Type.ERROR);
            return null;
        }

    }


    private boolean checkEmptyFields() {
        if (visitorId.getText().isEmpty() || fName.getText().isEmpty() || lName.getText().isEmpty() || DOB.getEditor().getText().isEmpty() || NIC.getText().isEmpty() || number.getText().isEmpty() || address.getText().isEmpty() || gender.getSelectionModel().isEmpty() || visitorType.getSelectionModel().isEmpty()) {
            return false;
        } else {
            System.out.println("Not Empty");
            return true;
        }
    }


    private void clearFields() {
        visitorId.clear();
        fName.clear();
        lName.clear();
        DOB.getEditor().clear();
        NIC.clear();
        number.clear();
        address.clear();
        gender.getSelectionModel().clearSelection();
        visitorType.getSelectionModel().clearSelection();
    }

}
