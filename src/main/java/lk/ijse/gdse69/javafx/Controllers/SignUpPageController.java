package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.gdse69.javafx.Animation.FlogAnimation;
import lk.ijse.gdse69.javafx.LoadFont.LoadFont;
import lk.ijse.gdse69.javafx.Model.User;
import lk.ijse.gdse69.javafx.Repository.UserRepo;
import lk.ijse.gdse69.javafx.jbcrypt.PasswordHasher;
import lk.ijse.gdse69.javafx.smtp.Mail;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPageController {
    public Text signUpMainName;
    public Text uPassText;
    public PasswordField uPassField;
    public TextField uFNameField;
    public Text uFNameText;
    public Text uLNameText;
    public TextField uLNameField;
    public Text uComPassText;
    public PasswordField uComPassField;
    public Text uEmailText;
    public TextField uEmailField;
    public Text uOtpText;
    public TextField uOtpField;
    public Button sendOtpBtn;
    public Button signUpBtn;
    public Label createAccLable;

    private Integer otpMail;


    public void initialize() {

        new FlogAnimation(signUpMainName,"New Magazine Prison");
        setFont();
    }

    private void setFont() {
        LoadFont loadFont = new LoadFont();
        Font font = loadFont.checkFont("Hammersmith One");
        createAccLable.setFont(font);
        createAccLable.setStyle("-fx-font-family: 'Hammersmith One';");

        System.out.println(createAccLable.getFont());

        signUpMainName.setFont(font);
        signUpMainName.setStyle("-fx-font-family: 'Hammersmith One';");

        Font font1=loadFont.checkFont("KanitBold");
        uFNameText.setFont(font1);
        uLNameText.setFont(font1);
        uEmailText.setFont(font1);
        uOtpText.setFont(font1);
        uPassText.setFont(font1);
        uComPassText.setFont(font1);
        uFNameText.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");
        uLNameText.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");
        uEmailText.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");
        uOtpText.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");
        uPassText.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");
        uComPassText.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");

        signUpBtn.setFont(font1);
        signUpBtn.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");

    }

    public void sendOtpBtn(){
        String recipientEmail = uEmailField.getText();

        if (!recipientEmail.isEmpty()){

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

                sendAlert("Email is Invalid. Please Enter Valid Email");
            }
        }else {

            sendAlert("Email is Empty. Please Enter Email");
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void sendAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("New Magazine Prison");
        alert.setContentText(contentText);
        alert.show();
    }

    public void signUpBtn() throws SQLException {

        String fName = uFNameField.getText();
        String lName = uLNameField.getText();
        String email = uEmailField.getText();
        String otp = uOtpField.getText();
        String pass = uPassField.getText();
        String comPass = uComPassField.getText();
        
        String fullName = fName + " " + lName;

        if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() || otp.isEmpty() || pass.isEmpty() || comPass.isEmpty()){

            sendAlert("Empty Fields. Please Enter All Fields");
        }else {
            if (checkPass(pass,comPass)){
                if (otp.equals(otpMail.toString())){

                    //TODO: Add to Database
                    System.out.println("all fields are correct");
                    
                    String uId =createUserId(lName);
                    System.out.println("user id >> "+uId);

                    UserRepo userRepo = new UserRepo();
                    User user = null;

                    if (userRepo.search(uId)== null){
                        String hashPass =  passHash(pass);
                        System.out.println("Hash Pass >> "+hashPass);

                        user = new User(uId,fullName,hashPass,email);
                    }else {
                        sendAlert("User Already Exists. Please Enter Another User Name");
                    }

                    try {

                        if(userRepo.save(user)){
                            sendAlert("User Registered Successfully\n Your User ID is : "+uId);
                            clearFields();
                        }else {
                            sendAlert("User Not Registered. Please Try Again");
                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }else {
                sendAlert("Password is not match. Please Enter Correct Password");
            }
        }
    }
    private void clearFields() {
        uFNameField.clear();
        uLNameField.clear();
        uEmailField.clear();
        uOtpField.clear();
        uPassField.clear();
        uComPassField.clear();
    }

    private String passHash(String pass) {
        PasswordHasher passwordHasher = new PasswordHasher();
        return passwordHasher.hashPassword(pass);
    }

    private String createUserId(String lName) {

        String lastNamePrefix = lName.substring(0, Math.min(3, lName.length()));
        String otpPrefix = otpMail.toString().substring(0, Math.min(4, otpMail.toString().length()));
        String userId = lastNamePrefix + otpPrefix;
        return userId;
    }

    private boolean checkPass(String pass, String comPass) {

        if (pass.equals(comPass)){
            return true;
        }else {
            return false;
        }
    }

    public void backBtn(ActionEvent actionEvent) throws IOException {
        System.out.println("Back Btn");
        Stage stage = (Stage) uFNameText.getScene().getWindow();
        Parent rootNode = null;

        rootNode = FXMLLoader.load(getClass().getResource("/View/SignInPage.fxml"));

        stage.getScene().setRoot(rootNode);
        stage.setTitle("New Magazine Prison-Sign In");
    }

    public void txtOtpOnKeyTyped(KeyEvent keyEvent) {

    }
}
