package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse69.javafx.Model.Officer;
import lk.ijse.gdse69.javafx.Model.Visitor;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Repository.OfficerRepo;
import lk.ijse.gdse69.javafx.Repository.VisitorRepo;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

    public BarChart<String, Integer> DashBarChart;
    public BarChart<String, Integer> DashVisitorTypeBar;
    public ProgressBar inmateProgressBar;
    public ProgressBar officerProgressBar;
    public ProgressBar visitorProgressBar;
    public LineChart<String, Integer> lineCart;
    public TextField searchId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            setTotalCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setDateAndDay();
        setToolTip();
        try {
            setBarValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            setVisitorBarValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setSearchIds();
        setProgessValues();
        setLineChartValues();

    }

    private void setShortCutKey(Scene scene) throws IOException {

        if (scene == null) {
            System.out.println("scene is null");
        }else {
            scene.setOnKeyPressed(event -> {
                if (new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + d");
                    try {
                        createStage("/View/InmatePage.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + o");
                    try {
                        createStage("/View/OfficerPage.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + v");
                    try {
                        createStage("/View/VisitorPage.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + s");
                    try {
                        createStage("/View/SectionPage.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN).match(event)) {
                    System.out.println("click ctrl + e");
                    try {
                        createStage("/View/financialPage.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

    }

    private void setSearchIds() {
        List<String> models = new ArrayList<>();
        models.add("Inmate");
        models.add("Officer");
        models.add("Visitor");
        models.add("Section");
        models.add("Expenses");

        String[] possibleNames = models.toArray(new String[0]);

        TextFields.bindAutoCompletion(searchId, possibleNames);
    }

    private void setLineChartValues() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Jan", 300));
        series.getData().add(new XYChart.Data<>("Feb", 400));
        series.getData().add(new XYChart.Data<>("Mar", 600));
        series.getData().add(new XYChart.Data<>("Apr", 500));
        series.getData().add(new XYChart.Data<>("May", 500));
        series.getData().add(new XYChart.Data<>("Jun", 800));
        series.getData().add(new XYChart.Data<>("Jul", 600));
        series.getData().add(new XYChart.Data<>("Aug", 700));
        series.getData().add(new XYChart.Data<>("Sep", 800));
        series.getData().add(new XYChart.Data<>("Oct", 700));
        series.getData().add(new XYChart.Data<>("Nov", 900));
        series.getData().add(new XYChart.Data<>("Dec", 1100));

        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();

        series2.getData().add(new XYChart.Data<>("Jan", 200));
        series2.getData().add(new XYChart.Data<>("Feb", 300));
        series2.getData().add(new XYChart.Data<>("Mar", 400));
        series2.getData().add(new XYChart.Data<>("Apr", 500));
        series2.getData().add(new XYChart.Data<>("May", 600));
        series2.getData().add(new XYChart.Data<>("Jun", 400));
        series2.getData().add(new XYChart.Data<>("Jul", 500));
        series2.getData().add(new XYChart.Data<>("Aug", 700));
        series2.getData().add(new XYChart.Data<>("Sep", 900));
        series2.getData().add(new XYChart.Data<>("Oct", 1100));
        series2.getData().add(new XYChart.Data<>("Nov", 1200));
        series2.getData().add(new XYChart.Data<>("Dec", 1100));


        lineCart.getData().add(series);
        lineCart.getData().add(series2);
    }

    private void setProgessValues() {
        inmateProgressBar.setProgress(0.0);
        inmateProgressBar.setProgress(0.8);

        officerProgressBar.setProgress(0.0);
        officerProgressBar.setProgress(0.5);

        visitorProgressBar.setProgress(0.0);
        visitorProgressBar.setProgress(0.2);

        visitorProgressBar.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                try {
                    setShortCutKey(newScene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setVisitorBarValues() throws SQLException {
        List<Visitor> allVisitors = VisitorRepo.getAllVisitors();

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        long familyCounts = allVisitors.stream().filter(visitor -> visitor.getVisitorType().equals("Immediate Family")).count();
        long friendCounts = allVisitors.stream().filter(visitor -> visitor.getVisitorType().equals("Legal Representative")).count();
        long legalCounts = allVisitors.stream().filter(visitor -> visitor.getVisitorType().equals("Officials")).count();
        long otherCounts = allVisitors.stream().filter(visitor -> visitor.getVisitorType().equals("Others")).count();

        series.getData().add(new XYChart.Data<>("Fam", (int) familyCounts));
        series.getData().add(new XYChart.Data<>("Leg",(int) friendCounts));
        series.getData().add(new XYChart.Data<>("Off", (int) legalCounts));
        series.getData().add(new XYChart.Data<>("Oth", (int) otherCounts));

        DashVisitorTypeBar.setTitle("Visitor");
        DashVisitorTypeBar.getData().add(series);

        for (XYChart.Series<String, Integer> s : DashVisitorTypeBar.getData()) {
            for (XYChart.Data<String, Integer> data : s.getData()) {
                data.getNode().setStyle("-fx-bar-fill: #0775a4;"); // Change color to blue (#0000FF)
            }
        }
    }

    private void setBarValues() throws SQLException {
        List<Officer> allOfficers = OfficerRepo.getAllOfficers();

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        long sergeantCounts = allOfficers.stream().filter(officer -> officer.getPosition().equals("Sergeant")).count();
        long lieCounts = allOfficers.stream().filter(officer -> officer.getPosition().equals("Lieutenant")).count();
        long capCounts = allOfficers.stream().filter(officer -> officer.getPosition().equals("Captain")).count();
        long majCounts = allOfficers.stream().filter(officer -> officer.getPosition().equals("Major")).count();
        long colCounts = allOfficers.stream().filter(officer -> officer.getPosition().equals("Colonel")).count();
        long genCounts = allOfficers.stream().filter(officer -> officer.getPosition().equals("General")).count();
        long suCounts = allOfficers.stream().filter(officer -> officer.getPosition().equals("Special Unit")).count();

        series.getData().add(new XYChart.Data<>("Ser", (int) sergeantCounts));
        series.getData().add(new XYChart.Data<>("Lie",(int) lieCounts));
        series.getData().add(new XYChart.Data<>("Cap", (int) capCounts));
        series.getData().add(new XYChart.Data<>("Maj", (int) majCounts));
        series.getData().add(new XYChart.Data<>("Col", (int) colCounts));
        series.getData().add(new XYChart.Data<>("Gen", (int) genCounts));
        series.getData().add(new XYChart.Data<>("SpU", (int) suCounts));

        DashBarChart.setStyle("-fx-background-color: transpanent;");
        DashBarChart.setTitle("Officer");
        DashBarChart.getData().add(series);
        for (XYChart.Series<String, Integer> s : DashBarChart.getData()) {
            for (XYChart.Data<String, Integer> data : s.getData()) {
                data.getNode().setStyle("-fx-bar-fill: #0775a4;"); // Change color to blue (#0000FF)
            }
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

    public void searchIdField(ActionEvent actionEvent) {
        String text = searchId.getText();

        switch (text){
            case "Inmate":
                try {
                    createStage("/View/InmatePage.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Officer":
                try {
                    createStage("/View/OfficerPage.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Visitor":
                try {
                    createStage("/View/VisitorPage.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Section":
                try {
                    createStage("/View/SectionPage.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Expenses":
                try {
                    createStage("/View/financialPage.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
