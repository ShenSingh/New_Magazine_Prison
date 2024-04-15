package lk.ijse.gdse69.javafx.Controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.gdse69.javafx.Animation.FlogAnimation;
import lk.ijse.gdse69.javafx.LoadFont.LoadFont;
import lk.ijse.gdse69.javafx.Sound.Sound;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SignInPageController {

    public Text signInMainName;

    public Text uNameText;
    public Text uPassText;
    public TextField uNameField;
    public PasswordField uPassField;
    public Button signInBtn;
    public Text creatAccText;
    public Button signInPageSignUpBtn;
    @FXML
    private Label loginLabel;

    private static Stage dashBoardStage= null;

    public void initialize() {

        new FlogAnimation(signInMainName,"New Magazine Prison");
        setFont();
    }

    private void setFont() {
        LoadFont loadFont = new LoadFont();
        Font font = loadFont.checkFont("Hammersmith One");
        loginLabel.setFont(font);
        loginLabel.setStyle("-fx-font-family: 'Hammersmith One';");

        signInMainName.setFont(font);
        signInMainName.setStyle("-fx-font-family: 'Hammersmith One';");

        Font font1=loadFont.checkFont("KanitBold");
        uNameText.setFont(font1);
        uPassText.setFont(font1);
        uNameText.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");
        uPassText.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");

        signInBtn.setFont(font1);
        signInBtn.setStyle("-fx-font-family: 'Kanit'; -fx-font-weight: bold;");
    }

    public void signInBtn(ActionEvent actionEvent) throws SQLException {

        Sound sound =new Sound();
        sound.playDoubleClickSound();
        System.out.println("sign In btn click");

        String uId = uNameField.getText();
        String uPass = uPassField.getText();

        if(/*UserRepo.valid(uId,uPass)*/ true){
            System.out.println("Login Success");
            Stage stage = (Stage) signInBtn.getScene().getWindow();
            dashBoardStage = new Stage();
            StackPane root = setStartVideo();
            Scene scene = new Scene(root, 800, 480);

            stage.setTitle("New Magazine Prison-Loading...");
            stage.setResizable(false);
            stage.setScene(scene);
            try {
                // Load FXML file
                Parent rootNode = FXMLLoader.load(getClass().getResource("/View/DashBoard.fxml"));
                Scene signInScene = new Scene(rootNode,1275,635);
                int displayDurationMillis = 3000;// 5s
                PauseTransition delay = new PauseTransition(Duration.millis(displayDurationMillis));
                delay.setOnFinished(event ->{
                    stage.hide();
                    dashBoardStage.setScene(signInScene);
                    dashBoardStage.setResizable(false);
                    dashBoardStage.initStyle(StageStyle.UNDECORATED);
                    dashBoardStage.show();
                });
                delay.play();
            } catch (Exception e) {
                e.printStackTrace(); // Print any exception to console
                // Handle the exception as necessary
            }
        }else {
            System.out.println("Login Failed");
            sendAlert("Invalid User Name or Password");
            uNameField.setText("");
            uPassField.setText("");
        }
    }
    public static Stage getDashBoardStage() {
        return dashBoardStage;
    }
    private void sendAlert(String contentText) {
        new Sound().playNotifySound();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("New Magazine Prison");
        alert.setContentText(contentText);
        alert.show();
    }
    public void signUpBtn(ActionEvent actionEvent) throws IOException {

        Sound sound =new Sound();
        sound.playDoubleClickSound();

        Stage stage = (Stage) signInBtn.getScene().getWindow();

        Parent rootNode = FXMLLoader.load(getClass().getResource("/View/SignUpPage.fxml"));

        stage.getScene().setRoot(rootNode);
        stage.setTitle("New Magazine Prison-Sign Up");
        stage.show();

    }
    public void backBtn(ActionEvent actionEvent) {
        System.exit(0);
    }
    public StackPane setStartVideo() {

        String videoPath = "/home/shen/Documents/myProject/NewManazinePrison/src/main/resources/Videos/lodingV.mp4";
        Media media = new Media(new File(videoPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        return root;
    }
}
