package lk.ijse.gdse69.javafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

    public void initialize(){
        Tooltip.install(inmateBtn, new Tooltip("Inmate Management"));
        Tooltip.install(officerBtn, new Tooltip("Officer Management"));
        Tooltip.install(dashBoardBtn, new Tooltip("DashBoard"));
        Tooltip.install(settingBtn, new Tooltip("Setting"));
        Tooltip.install(manyBtn, new Tooltip("Financial Management"));
        Tooltip.install(sectionBtn, new Tooltip("Section Management"));
        Tooltip.install(visitorBtn, new Tooltip("Visitor Management"));

        System.out.println("DashBoard Page");
    }



}
