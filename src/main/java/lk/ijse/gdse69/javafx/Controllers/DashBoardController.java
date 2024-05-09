package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.OfficerRepo;
import lk.ijse.gdse69.javafx.Repository.VisitorRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DashBoardController  extends MainDashBoard{

    @FXML
    public ImageView sirLankaLogo;
    public AnchorPane MainAnchorPane;
    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;


    public Text setDayType;
    public Text setDate;
    public Text totalInmateCount;
    public Text totalOfficerCount;
    public Text totalVisitorCount;

    public void initialize() throws SQLException {
        Tooltip.install(inmateBtn, new Tooltip("Inmate Management"));
        Tooltip.install(officerBtn, new Tooltip("Officer Management"));
        Tooltip.install(dashBoardBtn, new Tooltip("DashBoard"));
        Tooltip.install(settingBtn, new Tooltip("Setting"));
        Tooltip.install(manyBtn, new Tooltip("Financial Management"));
        Tooltip.install(sectionBtn, new Tooltip("Section Management"));
        Tooltip.install(visitorBtn, new Tooltip("Visitor Management"));

        setTotalCount();
        setDateAndDay();

        System.out.println("DashBoard Page");
    }

    private void setDateAndDay() {
        LocalDate date = LocalDate.now();
        setDate.setText(date.toString());

        LocalTime currentTime = LocalTime.now();

        // Set the greeting message based on the time of day
        String greetingMessage;
        if (currentTime.isBefore(LocalTime.NOON)) {
            greetingMessage = "Good Morning";
        } else if (currentTime.isBefore(LocalTime.of(18, 0))) {
            greetingMessage = "Good Afternoon";
        } else {
            greetingMessage = "Good Evening";
        }

        this.setDayType.setText(greetingMessage);
    }

    private void setTotalCount() throws SQLException {
        totalInmateCount.setText(String.valueOf(InmateRepo.getAllInmates().size())+" Inmates");
        totalOfficerCount.setText(String.valueOf(OfficerRepo.getAllOfficers().size())+" Officers");
        totalVisitorCount.setText(String.valueOf(VisitorRepo.getAllVisitors().size())+" Visitors");
    }


    public void addInmateBtn(ActionEvent actionEvent) throws IOException {
        createStage("/View/AddInmate.fxml");
    }

    public void addVisitorBtn(ActionEvent actionEvent) throws IOException {
        createStage("/View/AddVisitor.fxml");
    }

    public void addOfficerBtn(ActionEvent actionEvent) throws IOException {
        createStage("/View/AddOfficer.fxml");
    }

    public void addSectionBtn(ActionEvent actionEvent) throws IOException {
        createStage("/View/AddSection.fxml");
    }

    public void addEncidentBtn(ActionEvent actionEvent) throws IOException {
        createStage("/View/AddIcident.fxml");
    }
}
