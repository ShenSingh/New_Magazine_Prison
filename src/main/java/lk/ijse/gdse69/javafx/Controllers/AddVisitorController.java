package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.FlogQRCode.QRCodeGenerator;
import lk.ijse.gdse69.javafx.Model.*;
import lk.ijse.gdse69.javafx.Repository.*;
import lk.ijse.gdse69.javafx.Util.Util;
import org.controlsfx.control.textfield.TextFields;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

    @FXML
    private TextField searchVisitorId;

    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;

    static String visitorIdString;

    private byte[] imageDate;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        searchVisitorId();
        setToolTip();

        try {
            totalVisitorCount.setText(String.valueOf(VisitorRepo.getAllVisitors().size())+" Visitors");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setValuesComboBox();
        setNextVisitorId();

        
    }

    private void setNextVisitorId() {
        try {
            List<Visitor> allVisitor = VisitorRepo.getAllVisitors();
            if (allVisitor.size() == 0){
                visitorId.setText("V001");
            }
            else {
                int lastVisitorId = Integer.parseInt(allVisitor.get(allVisitor.size()-1).getVisitorID().substring(1));
                lastVisitorId++;
                if (lastVisitorId < 10){
                    visitorId.setText("V00"+lastVisitorId);
                }
                else if (lastVisitorId < 100){
                    visitorId.setText("V0"+lastVisitorId);
                }
                else {
                    visitorId.setText("V"+lastVisitorId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        visitorId.setEditable(false);
    }

    private void setValuesComboBox() {
        gender.getItems().addAll("Male", "Female");
        visitorType.getItems().addAll("Immediate Family", "Legal Representative", "Officials", "Others");
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

    private void searchVisitorId() {
        List<String> visitorIds = new ArrayList<>();

        try {
            List<Visitor> allVisitors = VisitorRepo.getAllVisitors();
            for (Visitor visitor : allVisitors) {
                visitorIds.add(visitor.getVisitorID()+" - "+visitor.getVisitorFirstName()+" "+visitor.getVisitorLastName());
            }
            String[] possibleNames = visitorIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(searchVisitorId, possibleNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                ShowAlert.showErrorNotify("Inmate Not Found");
            }
        }else{
            ShowAlert.showErrorNotify("Please Enter Inmate ID");
        }
    }
    public void canselBtn(ActionEvent actionEvent) {
        clearFields();
    }
    public void createQrBtn(ActionEvent actionEvent) {
        if (checkEmptyFields()) {
            String newVisitorId = visitorId.getText();

            if (checkValidVisitorName()){} else {return;}


                String filePath = "src/main/resources/QRCodeStore/"+newVisitorId+".png";
                boolean isGenerated = QRCodeGenerator.generateQRCode(newVisitorId);
                if (isGenerated){
                    createVisitorObject();
                    showQRCodeDialog(filePath);
                    String nextVisitorRecordId = null;
                    try {
                        nextVisitorRecordId = getNextVisitorRecordId(VisitorRecordRepo.getAllVisitorRecords());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    visitorRecordId.setText(nextVisitorRecordId);
                    visitorRecordId.setEditable(false);
                } else {
                    ShowAlert.showErrorNotify("Error Generating QR Code");
                }
        } else {
            ShowAlert.showErrorNotify("Please Fill All Fields");
        }
    }
    public void captureImage() throws IOException {
        try {
            ProcessBuilder builder = new ProcessBuilder("python3","/home/shen/Documents/myProject/NewManazinePrison/pyCapturePhoto/app.py");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String imagepath = null;
            String line;
            while ((line = reader.readLine()) != null) {
                imagepath = line;
            }
            int exitCode = process.waitFor();
            File file = new File(imagepath);
            this.imageDate = Util.readImage(file);

            showImage(imageDate);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void showImage(byte[] imageDate) {
        Image image = Util.showImage(imageDate);
        Alert qrCodeAlert = new Alert(Alert.AlertType.INFORMATION);
        qrCodeAlert.setTitle("Visitor Image");
        qrCodeAlert.setHeaderText("Visitor Profile Image");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        qrCodeAlert.getDialogPane().setContent(imageView);
        qrCodeAlert.showAndWait();
    }
    private String getNextVisitorRecordId(List<VisitorRecord> allVisitorRecords) {
        String maxId = "VR000";
        for (VisitorRecord visitorRecord : allVisitorRecords) {
            String currentId = visitorRecord.getVisitorRecordId();
            // Compare and update maxId if currentId is greater
            if (currentId.compareTo(maxId) > 0) {
                maxId = currentId;
            }
        }
        int idNumber = Integer.parseInt(maxId.substring(2)) + 1;
        return "VR" + String.format("%03d", idNumber);
    }
    private boolean checkValidVisitorName() {
        String newFName = fName.getText();
        String newLName = lName.getText();
        if (newFName.matches("[a-zA-Z]+") && newLName.matches("[a-zA-Z]+")){
            return true;
        } else {
            ShowAlert.showErrorNotify("Invalid Name. Name should contain only letters");
            return false;
        }
    }
    public static void showQRCodeDialog(String filePath) {
        Alert qrCodeAlert = new Alert(Alert.AlertType.INFORMATION);
        qrCodeAlert.setTitle("QR Code");
        qrCodeAlert.setHeaderText("Visitor QR Code");
        File file = new File(filePath);
        Image qrCodeImage = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(qrCodeImage);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        qrCodeAlert.getDialogPane().setContent(imageView);
        qrCodeAlert.showAndWait();
    }
    public void submitBtn(ActionEvent actionEvent) throws SQLException {
        if (createVisitorObject() != null && checkRecordEmptyFields()) {

            createVisitorRecordObject();

            SetFirstVisitorRecord setFirstVisitorRecord = new SetFirstVisitorRecord(createVisitorObject(),createVisitorRecordObject());
            boolean isSaved =SetFirstVisitorRecordRepo.save(setFirstVisitorRecord);
            if (isSaved){
                ShowAlert.showSuccessNotify("Visitor Record Saved Successfully");
                clearFields();
                this.inmateId.clear();
                this.visitorRecordId.clear();
                this.inmateName.clear();
                this.inmateNIC.clear();
            } else {
                ShowAlert.showErrorNotify("Error Saving Visitor Record");
            }
        } else {
        ShowAlert.showErrorNotify("Please Fill All Fields");
        }
    }

    private boolean checkRecordEmptyFields() {
        return Util.checkEmptyFields(visitorRecordId.getText(), inmateId.getText(), inmateName.getText(), inmateNIC.getText());
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
            if (imageDate == null){
                ShowAlert.showErrorNotify("Please Capture Image");
                return null;
            }
            String newVisitorId = visitorId.getText();
            String newFName = fName.getText();
            String newLName = lName.getText();
            String dateString = DOB.getEditor().getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            Date newDOB = Date.valueOf(localDate);

            String newNIC = NIC.getText();
            Integer newNumber = Integer.valueOf(number.getText());
            String newAddress = address.getText();
            String newGender=gender.getSelectionModel().getSelectedItem();
            String newVisitorType=visitorType.getSelectionModel().getSelectedItem();

            Visitor newVisitor = new Visitor(newVisitorId,newFName,newLName,newDOB,newNIC,newNumber,newAddress,newVisitorType,newGender,imageDate);
            return newVisitor;
        }else{
            ShowAlert.showErrorNotify("Please Fill All Fields");
            return null;
        }
    }
    private boolean checkEmptyFields() {
        return Util.checkEmptyFields(visitorId.getText(), fName.getText(), lName.getText(), DOB.getEditor().getText(), NIC.getText(), number.getText(),address.getText(),gender.getSelectionModel().getSelectedItem(),visitorType.getSelectionModel().getSelectedItem());
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
        setNextVisitorId();
    }
    public void takePhotoBtn(ActionEvent actionEvent) {
        try {
            captureImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getVisitorIdString() {
        return visitorIdString;
    }

    public void searchVisitorIdOnAction(ActionEvent actionEvent) throws IOException {
        //
        visitorIdString = this.searchVisitorId.getText();
        createStage("/View/VisitorProfile.fxml");
    }
}