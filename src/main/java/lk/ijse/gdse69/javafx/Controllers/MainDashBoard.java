package lk.ijse.gdse69.javafx.Controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse69.javafx.Launcher;

import java.io.IOException;

public class MainDashBoard implements MainDashBoardInterFace{

    @Override
    public void onInmate() throws IOException {
        this.createStage("/View/InmatePage.fxml");

    }

    @Override
    public void onOfficer() throws IOException {
        createStage("/View/OfficerPage.fxml");

    }

    @Override
    public void onVisitor() throws IOException {
        createStage("/View/VisitorPage.fxml");

    }

    @Override
    public void onSection() throws IOException {

        createStage("/View/SectionPage.fxml");
    }

    @Override
    public void onMany() throws IOException {

        createStage("/View/FinancialPage.fxml");
    }

    @Override
    public void onSetting() throws IOException {
        createStage("/View/SettingPage.fxml");
    }

    @Override
    public void onDashBord() throws IOException {
        Stage stage = WelcomeController.getDashBoardStage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/DashBoard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void onLogOut() {

        WelcomeController.getDashBoardStage().hide();
        Stage stage = Launcher.getStage();
        StackPane root =  new WelcomeController().setStartVideo();

        Scene videoScene = new Scene(root,803,480);

        stage.setScene(videoScene);
        try {
            // Load FXML file
            Parent rootNode = FXMLLoader.load(getClass().getResource("/View/Welcome.fxml"));
            Scene signInScene = new Scene(rootNode);
            int displayDurationMillis = 3000;// 1s
            PauseTransition delay = new PauseTransition(Duration.millis(displayDurationMillis));
            delay.setOnFinished(event -> stage.setScene(signInScene));
            delay.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.show();
    }
    @Override
    public void systemCloseBtn(){
        System.exit(0);
    }
    @Override
    public void miniHideBtn() {
        Stage stage = WelcomeController.getDashBoardStage();
        stage.setIconified(true);
    }

//    inmate method //
    @Override
    public void onAddInmateBtn() throws IOException {
        createStage("/View/AddInmate.fxml");
    }

    @Override
    public void onDeleteInmateBtn() throws IOException {
        createStage("/View/DeleteInmate.fxml");

    }

    @Override
    public void onUpdateInmateBtn() throws IOException {
        createStage("/View/UpdateInmate.fxml");
    }

    @Override
    public void onViewInmateBtn() throws IOException {
        createStage("/View/ViewInmate.fxml");
    }

    @Override
    public void inmateProfileBtn() throws IOException {
        createStage("/View/InmateProfile.fxml");
    }

    @Override
    public void addRecordBtn() throws IOException {
        createStage("/View/AddRecord.fxml");
    }

    @Override
    public void deleteRecordBtn() throws IOException {
        createStage("/View/DeleteRecord.fxml");
    }

    @Override
    public void updateRecordBtn() throws IOException {
        createStage("/View/UpdateRecord.fxml");
    }

    @Override
    public void viewRecordBtn() throws IOException {
        createStage("/View/ViewRecord.fxml");
    }

    @Override
    public void createStage(String path) throws IOException {

        StackPane root1 =  new WelcomeController().setStartVideo();

        Scene videoScene = new Scene(root1,1270,650);



        try {
            Stage stage = WelcomeController.getDashBoardStage();
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + path);
            e.printStackTrace();
        }
    }
}
