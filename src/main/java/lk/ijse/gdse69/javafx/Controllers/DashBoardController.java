package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.OfficerRepo;
import lk.ijse.gdse69.javafx.Repository.VisitorRepo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ResourceBundle;

public class DashBoardController  extends MainDashBoard implements Initializable
{

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

    public GridPane showCalander;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setTotalCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setDateAndDay();
        setToolTip();
        showCalander();
    }

    private void showCalander() {
        showCalander = new GridPane();
        showCalander.setHgap(5);
        showCalander.setVgap(5);

        YearMonth yearMonth = YearMonth.now();
        LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        int daysInMonth = yearMonth.lengthOfMonth();

        for (int i = 1; i <= daysInMonth; i++) {
            int dayOfWeek = date.getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday
            int dayOfMonth = date.getDayOfMonth();

            Circle circle = new Circle(10);
            circle.setFill(Color.RED); // Change color as needed

            Label dayLabel = new Label(Integer.toString(dayOfMonth));
            showCalander.add(circle, dayOfWeek - 1, (dayOfMonth + 6) / 7);
            showCalander.add(dayLabel, dayOfWeek - 1, (dayOfMonth + 6) / 7);

            date = date.plusDays(1);



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
