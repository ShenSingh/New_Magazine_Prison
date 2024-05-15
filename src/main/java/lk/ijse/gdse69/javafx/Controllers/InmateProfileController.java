package lk.ijse.gdse69.javafx.Controllers;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.gdse69.javafx.Alert.ShowAlert;
import lk.ijse.gdse69.javafx.Model.Incident;
import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.Model.InmateRecord;
import lk.ijse.gdse69.javafx.Repository.IncidentRelatedInmateRepo;
import lk.ijse.gdse69.javafx.Repository.IncidentRepo;
import lk.ijse.gdse69.javafx.Repository.InmateRecordRepo;
import lk.ijse.gdse69.javafx.Repository.InmateRepo;
import lk.ijse.gdse69.javafx.Util.Util;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InmateProfileController extends  MainDashBoard implements Initializable {
    public TextField searchInmate;  // search Id //////////

    public TableColumn<InmateRecord, String> IPInmateId;
    public TableColumn<InmateRecord, String> IPSectionId;
    public TableColumn<InmateRecord, String> IPEntryDate;
    public TableColumn<InmateRecord, String> IPReleasedate;
    public TableColumn<InmateRecord, String> IPCaseStatus;
    public TableColumn<InmateRecord, String> IPCrime;
    public AnchorPane MainAnchorPane;
    public ImageView inmateImg;


    public TableColumn<Incident, String> iiIncidentId;
    public TableColumn<Incident, String> iiSectionId;
    public TableColumn<Incident, String> iiType;
    public TableColumn<Incident, String> iiDate;
    public TableColumn<Incident, String> iiTime;
    public TableColumn<Incident, String> iiDescription;


    @FXML
    private AnchorPane iconsPane;
    @FXML
    private AnchorPane incidentRecordAnchor;

    @FXML
    private TextField inmateId;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private TextField NIC;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private TextField address;
    @FXML
    private ComboBox<String> status;
    @FXML
    private DatePicker DOB;

    @FXML
    private Text fullName;

    @FXML
    private AnchorPane line1;
    @FXML
    private AnchorPane line2;
    @FXML
    private AnchorPane line3;
    @FXML
    private AnchorPane line4;
    @FXML
    private AnchorPane line5;
    @FXML
    private AnchorPane line6;
    @FXML
    private AnchorPane line7;
    @FXML
    private AnchorPane line8;

    private boolean isIconNamesVisible1 = false;
    private boolean isIconNamesVisible2 = false;
    private TranslateTransition slideTransition1;
    private TranslateTransition slideTransition2;

    private boolean isEditingEnabled = false;


    @FXML
    public Button inmateBtn;
    public Button officerBtn;
    public Button dashBoardBtn;
    public Button settingBtn;
    public Button manyBtn;
    public Button sectionBtn;
    public Button visitorBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValuesComboBoxes();

        // Initialize translate transitions for iconsPane
        slideTransition1 = new TranslateTransition(Duration.seconds(0.3), iconsPane);
        slideTransition1.setToX(450); // Slide distance to hide names initially

        // Initialize translate transitions for incidentRecordAnchor
        slideTransition2 = new TranslateTransition(Duration.seconds(0.3), incidentRecordAnchor);
        slideTransition2.setToX(450); // Slide distance to hide names initially
        iconsPane.setVisible(false);
        incidentRecordAnchor.setVisible(false);

        inmateId.setEditable(false);

        searchInmateNonPage();
        setSearchIds();

        setToolTips();
    }

    private void setToolTips() {
        Tooltip.install(inmateBtn, new Tooltip("Inmate Management"));
        Tooltip.install(officerBtn, new Tooltip("Officer Management"));
        Tooltip.install(dashBoardBtn, new Tooltip("DashBoard"));
        Tooltip.install(settingBtn, new Tooltip("Setting"));
        Tooltip.install(manyBtn, new Tooltip("Financial Management"));
        Tooltip.install(sectionBtn, new Tooltip("Section Management"));
        Tooltip.install(visitorBtn, new Tooltip("Visitor Management"));
    }

    private void setSearchIds() {
        List<String> inmateIds = new ArrayList<>();

        try {
            List<Inmate> allInmates = InmateRepo.getAllInmates();
            for (Inmate inmate : allInmates) {
                inmateIds.add(inmate.getInmateId()+" - "+inmate.getInmateFirstName()+" "+inmate.getInmateLastName());
            }
            String[] possibleNames = inmateIds.toArray(new String[0]);

            TextFields.bindAutoCompletion(searchInmate, possibleNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setValuesComboBoxes() {
        gender.getItems().addAll("Male","Female");
        status.getItems().addAll("Active","Inactive");
    }

    @FXML
    public void showRecordBtn(ActionEvent actionEvent) {
        incidentRecordAnchor.setVisible(false);
        iconsPane.setVisible(true);
        // Toggle visibility and animate iconsPane
        isIconNamesVisible1 = !isIconNamesVisible1;
        if (isIconNamesVisible1) {
            slideTransition1.setToX(450); // Slide to reveal names
            centerContentHideShow(isIconNamesVisible1);
        } else {
            slideTransition1.setToX(0); // Slide to hide names
            centerContentHideShow(isIconNamesVisible1);
        }
        slideTransition1.play();
    }

    @FXML
    public void showIncidentRecordBtn(ActionEvent actionEvent) {
        iconsPane.setVisible(false);
        incidentRecordAnchor.setVisible(true);
        // Toggle visibility and animate incidentRecordAnchor
        isIconNamesVisible2 = !isIconNamesVisible2;
        if (isIconNamesVisible2) {
            slideTransition2.setToX(500); // Slide to reveal names
            centerContentHideShow(isIconNamesVisible2);
        } else {
            centerContentHideShow(isIconNamesVisible2);
            slideTransition2.setToX(0); // Slide to hide names
        }
        slideTransition2.play();
    }

    private void centerContentHideShow(boolean isEnebled) {
        inmateId.setVisible(isEnebled);
        fName.setVisible(isEnebled);
        lName.setVisible(isEnebled);
        NIC.setVisible(isEnebled);
        DOB.setVisible(isEnebled);
        address.setVisible(isEnebled);
        status.setVisible(isEnebled);
        gender.setVisible(isEnebled);

        line1.setVisible(isEnebled);
        line2.setVisible(isEnebled);
        line3.setVisible(isEnebled);
        line4.setVisible(isEnebled);
        line5.setVisible(isEnebled);
        line6.setVisible(isEnebled);
        line7.setVisible(isEnebled);
        line8.setVisible(isEnebled);
    }

    public void editProfileTogal(ActionEvent actionEvent) {
        isEditingEnabled = !isEditingEnabled;

        fName.setEditable(isEditingEnabled);
        lName.setEditable(isEditingEnabled);
        NIC.setEditable(isEditingEnabled);
        DOB.setEditable(isEditingEnabled);
        address.setEditable(isEditingEnabled);

        for (String item : gender.getItems()){
            gender.setDisable(!isEditingEnabled);
        }
        for (String item : status.getItems()){
            status.setDisable(!isEditingEnabled);
        }
    }

    public void searchInmateField(ActionEvent actionEvent) {
        String inmateId = searchInmate.getText().split(" - ")[0];

        if (inmateId.isEmpty()){
            ShowAlert.showErrorNotify("Please enter the inmate id");
            return;
        }

        try {
            Inmate inmate = InmateRepo.search(inmateId);
            if (inmate != null) {

                String fullName = inmate.getInmateFirstName() + " " + inmate.getInmateLastName();
                this.fullName.setText(fullName);


                getTableValues(InmateRecordRepo.getRecords(inmate.getInmateId()));
                this.inmateId.setText(inmate.getInmateId());
                this.fName.setText(inmate.getInmateFirstName());
                this.lName.setText(inmate.getInmateLastName());
                this.DOB.setValue(LocalDate.parse(inmate.getInmateDOB().toString()));
                this.NIC.setText(inmate.getInmateNIC());
                this.gender.getSelectionModel().select(inmate.getGender());
                this.address.setText(inmate.getInmateAddress());
                this.status.getSelectionModel().select(inmate.getStatus());

                inmateImg.setFitWidth(100);
                inmateImg.setFitHeight(100);
                Circle clip = new Circle(inmateImg.getFitWidth() / 2, inmateImg.getFitHeight() / 2, inmateImg.getFitWidth() / 2);
                inmateImg.setClip(clip);

                Image image = Util.showImage(inmate.getInmateImage());
                inmateImg.setImage(image);

            }else{
                ShowAlert.showErrorNotify("Inmate not found");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void searchInmateNonPage() {

        String inmateId = null;

        inmateId = SearchId.getInmateId();

        if (inmateId == null){
            return;
        }

        try {
            Inmate inmate = InmateRepo.search(inmateId);
            if (inmate != null) {

                String fullName = inmate.getInmateFirstName() + " " + inmate.getInmateLastName();
                this.fullName.setText(fullName);


                getTableValues(InmateRecordRepo.getRecords(inmate.getInmateId()));
                getIncidentValues(inmate.getInmateId());

                this.inmateId.setText(inmate.getInmateId());
                this.fName.setText(inmate.getInmateFirstName());
                this.lName.setText(inmate.getInmateLastName());
                this.DOB.setValue(LocalDate.parse(inmate.getInmateDOB().toString()));
                this.NIC.setText(inmate.getInmateNIC());
                this.gender.getSelectionModel().select(inmate.getGender());
                this.address.setText(inmate.getInmateAddress());
                this.status.getSelectionModel().select(inmate.getStatus());

                inmateImg.setFitWidth(100);
                inmateImg.setFitHeight(100);
                Circle clip = new Circle(inmateImg.getFitWidth() / 2, inmateImg.getFitHeight() / 2, inmateImg.getFitWidth() / 2);
                inmateImg.setClip(clip);

                Image image = Util.showImage(inmate.getInmateImage());
                inmateImg.setImage(image);

            }else{
                ShowAlert.showErrorNotify("Inmate not found");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void getIncidentValues(String inmateId) {
        List<String> incidentIds = IncidentRelatedInmateRepo.getIdByInmateId(inmateId);
        List<Incident> incidents = new ArrayList<>();
        for (String incidentId : incidentIds){
            try {
                if (IncidentRepo.search(incidentId) != null){
                    incidents.add(IncidentRepo.search(incidentId));
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        setIncidentTableValues(incidents);
    }

    private void setIncidentTableValues(List<Incident> incidents) {

        if (incidents == null){
            return;
        }

        iiIncidentId.setCellValueFactory(new PropertyValueFactory<>("incidentId"));
        iiSectionId.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
        iiType.setCellValueFactory(new PropertyValueFactory<>("incidentType"));
        iiDate.setCellValueFactory(new PropertyValueFactory<>("incidentDate"));
        iiTime.setCellValueFactory(new PropertyValueFactory<>("incidentTime"));
        iiDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        iiIncidentId.getTableView().setItems(FXCollections.observableArrayList(incidents));

    }

    private void getTableValues(List<InmateRecord> inmateRecords){

        IPInmateId.setCellValueFactory(new PropertyValueFactory<>("inmateId"));
        IPSectionId.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
        IPEntryDate.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        IPReleasedate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        IPCrime.setCellValueFactory(new PropertyValueFactory<>("crime"));
        IPCaseStatus.setCellValueFactory(new PropertyValueFactory<>("caseStatus"));

        IPCrime.getTableView().setItems(FXCollections.observableArrayList(inmateRecords));
    }

    public void inInmateProfileBtn(ActionEvent actionEvent) throws IOException {
        super.createStage("/View/InmateProfile.fxml");
    }

    public void inDeleteInmate(ActionEvent actionEvent) {
        if (searchInmate.getText().isEmpty()){
            ShowAlert.showErrorNotify("Please enter Inmate ID");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Delete Inmate");
        alert.setContentText("Are you sure you want to delete this Inmate?");

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);

        okButton.setOnAction(e -> {
            goDeleteInmate();

            alert.close();
        });
        cancelButton.setOnAction(e -> {
            alert.close();
        });

        alert.showAndWait();
    }
    private void goDeleteInmate(){
        String inmateId = searchInmate.getText().split(" - ")[0];;

        if (inmateId.isEmpty()){
            ShowAlert.showErrorNotify("Please enter the inmate id");
            return;
        }
        try {
            if (InmateRepo.delete(inmateId)){
                ShowAlert.showSuccessNotify("Inmate Deleted Successfully");
                clearFields();
                this.fullName.setText("");
            }else {
                ShowAlert.showErrorNotify("Inmate not deleted");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveBtn(ActionEvent actionEvent) throws SQLException {

        if (checkEmpty()){

            String inmateId = this.inmateId.getText();
            String fName = this.fName.getText();
            String lName = this.lName.getText();
            LocalDate DOB = LocalDate.parse(this.DOB.getValue().toString());
            String NIC = this.NIC.getText();
            String Gender = this.gender.getSelectionModel().getSelectedItem();
            String address = this.address.getText();
            String status = this.status.getSelectionModel().getSelectedItem();

            byte[] imageData =  InmateRepo.search(inmateId).getInmateImage();

            Inmate inmate = new Inmate(inmateId, fName, lName,DOB, NIC, Gender, address, status,imageData);

            if (InmateRepo.update(inmate)){
                ShowAlert.showSuccessNotify("Inmate Updated Successfully");

            }else {
             ShowAlert.showErrorNotify("Inmate not updated");

            }
        }else{
            ShowAlert.showErrorNotify("Empty Fields. Please fill all the fields");
        }
    }

    private boolean checkEmpty() {

        if (inmateId.getText().isEmpty() || fName.getText().isEmpty() || lName.getText().isEmpty() || NIC.getText().isEmpty() || DOB.getValue()==null ||
                address.getText().isEmpty() || status.getSelectionModel().getSelectedItem().isEmpty()){
            return false;
        }
        return true;
    }

    public void cancelBtn(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        inmateId.clear();
        fName.clear();
        lName.clear();
        NIC.clear();
        DOB.getEditor().clear();
        gender.getSelectionModel().clearSelection();
        address.clear();
        status.getSelectionModel().clearSelection();
        searchInmate.clear();
    }
}
