package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Controllers.ShowReports.InmateReports;
import lk.ijse.gdse69.javafx.Controllers.ShowReports.OfficerReports;
import lk.ijse.gdse69.javafx.Controllers.ShowReports.VisitorReports;
import lk.ijse.gdse69.javafx.Model.*;
import lk.ijse.gdse69.javafx.Repository.*;
import lk.ijse.gdse69.javafx.Util.Util;
import lk.ijse.gdse69.javafx.jbcrypt.PasswordHasher;
import lk.ijse.gdse69.javafx.smtp.Mail;

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
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingPageController extends MainDashBoard implements Initializable {
    public ImageView sirLankaLogo;


    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;
    public AnchorPane MainAnchorPane;
    public ImageView mainProfilePic;
    public Text fullName;
    
    public ImageView secondProfilePic;
    public TextField userId;
    public TextField userName;
    public TextField phone;
    public TextField dob;
    public TextField address1;
    public TextField address2;
    
    public TextField email;
    public TextField password;
    public TextField OTP;
    public TextField comPassword;

    public JFXButton sendOTPBtn;
    public JFXButton saveChangeBtn;

    private static String userIdValue;
    private static String userIdNewValue;

    private static String userNameValue;
    private static String phoneValue;
    private static String dobValue;
    private static String address1Value;
    private static String address2Value;
    private static String emailValue;
    private static String passwordValue;
    private static String comPasswordValue;
    private static byte[] imageDataValue;
    public Text topDate;

    public ImageView reportLeftRightImage;
    public AnchorPane reportMainAnchor;
    public JFXComboBox<String> mainSelectionCombo;
    public JFXComboBox<String> secondSelectionCombo;

    private Integer otpMail;

    private TranslateTransition sideTransition;
    private boolean isShow = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setToolTip();
        try {
            setUserData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        OTP.setEditable(false);
        comPassword.setEditable(false);
        password.setEditable(false);
        email.setEditable(false);
        sendOTPBtn.setDisable(true);
        saveChangeBtn.setDisable(true);

        setDate();
        setTransition();
        setMainComboValues();
    }

    private void setMainComboValues() {
        mainSelectionCombo.getItems().addAll("Inmate", "Officer", "Visitor");
    }

    private void setTransition() {
        sideTransition = new TranslateTransition(Duration.seconds(0.9), reportMainAnchor);
        sideTransition.setFromX(270);
        File file = new File("src/main/resources/images/icon/rightIcon.png");
        Image image = new Image(file.toURI().toString());
        reportLeftRightImage.setImage(image);

    }

    private void setDate() {

        LocalDate date = LocalDate.now();
        String dateString = String.valueOf(date);

        topDate.setText(dateString);
    }

    private void setUserData() throws SQLException {
        String id = WelcomeController.getFlogUId();

        User user = UserRepo.search(id);

        if (user != null){
            userIdValue = user.getUId();
            fullName.setText(user.getUName());
            userId.setText(user.getUId());
            userName.setText(user.getUName());
            email.setText(user.getUEmail());

            if (user.getPhone() != null){
                phone.setText(user.getPhone());
            }else {
                phone.setText("");
            }
            if (user.getDob() != null){
                dob.setText(user.getDob());
            }else {
                dob.setText("");
            }
            if (user.getAddressLine1() != null){
                address1.setText(user.getAddressLine1());
            }else {
                address1.setText("");
            }
            if (user.getAddressLine2() != null){
                address2.setText(user.getAddressLine2());
            }else {
                address2.setText("");
            }
            setImage(user.getImageData());

            userIdValue = user.getUId();
            userNameValue = user.getUName();
            phoneValue = user.getPhone();
            dobValue = user.getDob();
            address1Value = user.getAddressLine1();
            address2Value = user.getAddressLine2();
            emailValue = user.getUEmail();
            passwordValue = user.getUPassword();
            imageDataValue = user.getImageData();


        }else{
            ShowAlert.showErrorNotify("System Error. Please Log in Again.");
        }

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

    public void userDeleteBtn(ActionEvent actionEvent) throws SQLException {

        boolean isDeleted = UserRepo.delete(userIdValue);

        if (isDeleted){
            ShowAlert.showSuccessNotify("User Deleted Successfully.");
            onLogOut();
        }else{
            ShowAlert.showErrorNotify("User Delete Failed.");
        }
    }

    public void saveChangesBtn(ActionEvent actionEvent) throws SQLException {
        if (email.getText().isEmpty() || password.getText().isEmpty() || comPassword.getText().isEmpty() || OTP.getText().isEmpty()){
            ShowAlert.showErrorNotify("Please Fill All Fields.");
            return;
        }

        if (!password.getText().equals(comPassword.getText())){
            ShowAlert.showErrorNotify("Password and Confirm Password are not Same.");
            return;
        }

        if (otpMail == Integer.parseInt(OTP.getText())){

            userIdValue = userId.getText();
            userNameValue = userName.getText();
            phoneValue = phone.getText();
            dobValue = dob.getText();
            address1Value = address1.getText();
            address2Value = address2.getText();
            emailValue = email.getText();
            passwordValue = password.getText();
            imageDataValue = imageDataValue;

            passwordValue = passHash(passwordValue);

            User user = new User(userIdValue,userNameValue,emailValue,passwordValue,address1Value,address2Value,phoneValue,"",dobValue,imageDataValue);

            boolean isUpdated = UserRepo.update(user);

            if (isUpdated){
                ShowAlert.showSuccessNotify("User Details Updated Successfully.");
            }else{
                ShowAlert.showErrorNotify("User Details Update Failed.");
            }
        }else{
            ShowAlert.showErrorNotify("OTP is Incorrect.");
        }
    }
    private String passHash(String pass) {
        PasswordHasher passwordHasher = new PasswordHasher();
        return passwordHasher.hashPassword(pass);
    }

    public void sendOTP(ActionEvent actionEvent) throws SQLException {

        if (email.getText().isEmpty()){
            ShowAlert.showErrorNotify("Email is Empty.");
            return;
        }

        String recipientEmail = email.getText();



            if (isValidEmail(recipientEmail)){


                Random random = new Random();
                otpMail = random.nextInt(999999);


                Mail mail = new Mail();
                mail.setMsg("Welcome to New Magazine Prison.\n\nFor your first login you'll need the OTP.\nYour OTP is :" + otpMail + "\n" +
                        "\nTime : " +
                        Time.valueOf(LocalTime.now()) + "\n" +
                        "Date : " +
                        Date.valueOf(LocalDate.now()));//email message

                mail.setTo(recipientEmail); //receiver's mail
                mail.setSubject("OTP for New Magazine Prison"); //email subject
                mail.run();

            }else {

                ShowAlert.showErrorNotify("Invalid Email. Please Enter Valid Email");
            }
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void changeuserId(ActionEvent actionEvent) throws SQLException {

        if (userId.getText().isEmpty()){
            ShowAlert.showErrorNotify("User Id is Empty.");
            return;
        }
        if (userId.getText() == userIdValue){
            ShowAlert.showErrorNotify("User Id is Same as Previous.");
            return;
        }
        userIdNewValue = userId.getText();

        boolean isIdUpdated = UserRepo.updateId(userIdValue,userId.getText());

        if (isIdUpdated){
            userIdValue = userIdNewValue;
            ShowAlert.showSuccessNotify("User Id Updated");
        }else{
            ShowAlert.showErrorNotify("User Id Update Fail");
        }

        //boolean isupdated = UserRepo.update();

    }

    public void changeProfilePicBtn(ActionEvent actionEvent) {
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
            this.imageDataValue = Util.readImage(file);

            showImage(imageDataValue);
            setImage(imageDataValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setImage(byte[] imageDataValue) {
        Image image = Util.showImage(imageDataValue);

        Circle clip = new Circle(mainProfilePic.getFitWidth() / 2, mainProfilePic.getFitHeight() / 2, mainProfilePic.getFitWidth() / 2);
        mainProfilePic.setClip(clip);

        Circle clip2 = new Circle(secondProfilePic.getFitWidth() / 2, secondProfilePic.getFitHeight() / 2, secondProfilePic.getFitWidth() / 2);
        secondProfilePic.setClip(clip2);

        mainProfilePic.setImage(image);
        secondProfilePic.setImage(image);
    }

    private void showImage(byte[] imageDate) {
    Image image = Util.showImage(imageDate);
    Alert qrCodeAlert = new Alert(Alert.AlertType.INFORMATION);
    qrCodeAlert.setTitle("Inmate Image");
    qrCodeAlert.setHeaderText("Inmate Profile Image");
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(300);
    imageView.setFitHeight(300);
    qrCodeAlert.getDialogPane().setContent(imageView);
    qrCodeAlert.showAndWait();
}

    public void editSecDitails(ActionEvent actionEvent) {
        email.setEditable(true);
        password.setEditable(true);
        comPassword.setEditable(true);
        sendOTPBtn.setDisable(false);
        saveChangeBtn.setDisable(false);
        OTP.setEditable(true);
    }

    public void reportPaneShowHideBtn(ActionEvent actionEvent) {
        isShow = !isShow;

        if(isShow){
            File file = new File("src/main/resources/images/icon/leftArrowIcon.png");
            Image image = new Image(file.toURI().toString());
            reportLeftRightImage.setImage(image);
            sideTransition.setToX(270);

        }else{
            File file = new File("src/main/resources/images/icon/rightIcon.png");
            Image image = new Image(file.toURI().toString());
            reportLeftRightImage.setImage(image);
            sideTransition.setToX(0);

        }
        sideTransition.play();
    }

    public void mainSelection(ActionEvent actionEvent) throws SQLException {
        String selectItem = mainSelectionCombo.getSelectionModel().getSelectedItem();

        if (selectItem != null) {
            switch (selectItem) {
                case "Inmate":
                    //
                    setInmateValues(InmateRepo.getAllInmates());
                    break;
                case "Officer":
                    //
                    setOfficerValues(OfficerRepo.getAllOfficers());
                    break;
                case "Visitor":
                    //
                    setVisitorValues(VisitorRepo.getAllVisitors());
                    break;
                case "Expenses":
                    //
                    setExpensesValues(ExpencesRepo.getAllExpenses());
                    break;
                default:
                    break;
            }
        }
    }

    private void setExpensesValues(List<Expences> allExpenses) {
        secondSelectionCombo.getItems().add("All");
        for (Expences expences: allExpenses){
            secondSelectionCombo.getItems().add(expences.getExpenceId());
        }
    }

    private void setVisitorValues(List<Visitor> allVisitors) {
        secondSelectionCombo.getItems().add("All");
        for (Visitor visitor : allVisitors){
            secondSelectionCombo.getItems().add(visitor.getVisitorID());
        }
    }

    private void setOfficerValues(List<Officer> allOfficers) {
        secondSelectionCombo.getItems().add("All");
        for (Officer officer : allOfficers){
            secondSelectionCombo.getItems().add(officer.getOfficerId());
        }
    }

    private void setInmateValues(List<Inmate> allInmates) {

        secondSelectionCombo.getItems().add("All");
        for (Inmate inmate : allInmates){
            secondSelectionCombo.getItems().add(inmate.getInmateId());
        }
    }

    public void secondSelection(ActionEvent actionEvent) {
    }

    public void printReport(ActionEvent actionEvent) {

        String mainSelection = mainSelectionCombo.getSelectionModel().getSelectedItem();
        String secondSelection = secondSelectionCombo.getSelectionModel().getSelectedItem();

        if (mainSelection != null && secondSelection != null) {
            switch (mainSelection) {
                case "Inmate":
                    //
                    InmateReports.getSelection(secondSelection);
                    break;
                case "Officer":
                    //
                    OfficerReports.getSelection(secondSelection);
                    break;
                case "Visitor":
                    //
                    VisitorReports.getSelection(secondSelection);
                    break;
                case "Expenses":
                    //

                    break;
                default:
                    break;
            }
        }
    }
}
