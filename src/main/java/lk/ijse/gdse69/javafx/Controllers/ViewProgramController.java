package lk.ijse.gdse69.javafx.Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse69.javafx.Model.Program;
import lk.ijse.gdse69.javafx.Repository.ProgramRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewProgramController extends MainDashBoard implements Initializable {
    public AnchorPane MainAnchorPane;


    public TableColumn<Program, String> TprogramId;
    public TableColumn<Program, String> Tname;
    public TableColumn<Program, String> TsectionId;
    public TableColumn<Program, String> Tdate;
    public TableColumn<Program, String> Ttime;
    public TableColumn<Program, String> Tdescription;
    public JFXComboBox viewOptionCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewOptionCombo.getItems().addAll("All Programs", "Today Programs","This Month Programs");
        viewOptionCombo.setVisible(false);//not needed in this controller

        try {
            setTableValues(ProgramRepo.getAllPrograms());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setTableValues(List<Program> allPrograms) {

        TprogramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        Tname.setCellValueFactory(new PropertyValueFactory<>("programName"));
        TsectionId.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
        Tdate.setCellValueFactory(new PropertyValueFactory<>("programDate"));
        Ttime.setCellValueFactory(new PropertyValueFactory<>("programTime"));
        Tdescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        Tdescription.getTableView().setItems(FXCollections.observableArrayList(allPrograms));

    }
}
