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

        createStage("/View/financialPage.fxml");
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
        createStage("/View/InmateProfile.fxml");

    }

    @Override
    public void onUpdateInmateBtn() throws IOException {
        createStage("/View/InmateProfile.fxml");
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


//    officer method //
    @Override
    public void onAddOfficerBtn() throws IOException {
        createStage("/View/AddOfficer.fxml");
    }

    @Override
    public void onDeleteOfficerBtn() throws IOException {
        createStage("/View/OfficerProfile.fxml");

    }

    @Override
    public void onUpdateOfficerBtn() throws IOException {
        createStage("/View/OfficerProfile.fxml");
    }

    @Override
    public void onViewOfficerBtn() throws IOException {
        createStage("/View/ViewOfficer.fxml");
    }

    @Override
    public void officerProfileBtn() throws IOException {
        createStage("/View/OfficerProfile.fxml");
    }

//    sectionPage-program method //

    @Override
    public void onAddProgramBtn() throws IOException {
        createStage("/View/AddProgram.fxml");
    }

    @Override
    public void onDeleteProgramBtn() throws IOException {
        createStage("/View/ProgramProfile.fxml");

    }

    @Override
    public void onUpdateProgramBtn() throws IOException {
        createStage("/View/ProgramProfile.fxml");
    }

    @Override
    public void onViewProgramBtn() throws IOException {
        createStage("/View/ViewProgram.fxml");
    }


//   sectionPage-section method //

    @Override
    public void onAddSectionBtn() throws IOException {
        createStage("/View/AddSection.fxml");
    }

    @Override
    public void onDeleteSectionBtn() throws IOException {
        createStage("/View/SectionProfile.fxml");

    }

    @Override
    public void onUpdateSectionBtn() throws IOException {
        createStage("/View/SectionProfile.fxml");
    }

    @Override
    public void onViewSectionBtn() throws IOException {
        createStage("/View/ViewSection.fxml");
    }

    @Override
    public void sectionProfileBtn() throws IOException {
        createStage("/View/SectionProfile.fxml");

    }

    //    sectionPage-Incident method

    @Override
    public void onAddIncidentBtn() throws IOException {
        createStage("/View/AddIcident.fxml");
    }

    @Override
    public void onDeleteIncidentBtn() throws IOException {
        createStage("/View/IncidentSetting.fxml");
    }

    @Override
    public void onUpdateIncidentBtn() throws IOException {
        createStage("/View/IncidentSetting.fxml");
    }

    @Override
    public void onViewIncidentBtn() throws IOException {
        createStage("/View/ViewIncident.fxml");
    }

//    Visitor page method////////////////

    @Override
    public void onAddVisitorBtn() throws IOException {
        createStage("/View/AddVisitor.fxml");
    }

    @Override
    public void onDeleteVisitorBtn() throws IOException {
        createStage("/View/VisitorProfile.fxml");

    }

    @Override
    public void onUpdateVisitorBtn() throws IOException {
        createStage("/View/VisitorProfile.fxml");
    }

    @Override
    public void onViewVisitorBtn() throws IOException {
        createStage("/View/ViewVisitor.fxml");
    }

    @Override
    public void visitorProfileBtn() throws IOException {
        createStage("/View/VisitorProfile.fxml");

    }

    //   FinancialPage method

    @Override
    public void onAddExpencesBtn() throws IOException {
        createStage("/View/AddExpences.fxml");
    }

    @Override
    public void onDeleteExpencesBtn() throws IOException {
        createStage("/View/ExpensesSetting.fxml");
    }

    @Override
    public void onUpdateExpencesBtn() throws IOException {
        createStage("/View/ExpensesSetting.fxml");

    }

    @Override
    public void onViewExpencesBtn() throws IOException {
        createStage("/View/ViewExpenses.fxml");
    }

    @Override
    public void expensesProfileBtn() throws IOException {
        createStage("/View/ExpensesSetting.fxml");

    }


    @Override
    public void createStage(String path) throws IOException {

        StackPane root1 =  new WelcomeController().setStartVideo();

        Scene videoScene = new Scene(root1,1270,650);



        try {
            Stage stage = WelcomeController.getDashBoardStage();

            if (stage== null){
                stage = (Stage) new WelcomeController().suMainText1.getScene().getWindow();
            }


            Parent root = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + path);
            e.printStackTrace();
        }
    }
}
