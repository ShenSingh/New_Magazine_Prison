package lk.ijse.gdse69.javafx.Controllers;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.animation.KeyValue;

import java.io.IOException;


public class InmatePageController extends MainDashBoard{
    public ImageView sirLankaLogo;
    public AnchorPane mainAnchorPage;
    public JFXButton addRecordBtn;
    public JFXButton deleteRecordBtn;
    public JFXButton updateRecordBtn;
    public JFXButton viewRecordBtn;

    public void initialize(){

        System.out.println("Inmate Page");

        ////////////         You can bind the actions to the corresponding key combinations          /////////////////////////////////////

        //addButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN), this::handleAddAction);
        //addButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN), this::handleDeleteAction);
        //addButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.U, KeyCodeCombination.CONTROL_DOWN), this::handleUpdateAction);


        // Apply hover effect to buttons
        applyHoverEffect(addRecordBtn);
        applyHoverEffect(deleteRecordBtn);
        applyHoverEffect(updateRecordBtn);
        applyHoverEffect(viewRecordBtn);


    }

    private void applyHoverEffect(JFXButton button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), button);
        button.setOnMouseEntered(event -> {
            scaleTransition.setToX(1.05);
            scaleTransition.setToY(1.05);
            scaleTransition.play();
        });
        button.setOnMouseExited(event -> {
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });
    }


    public void onAddInmateBtn(ActionEvent actionEvent) throws IOException {
        System.out.println("Add Inmate Button Clicked");
        createStage("/View/AddInmate.fxml");
    }

    public void activeInmateBtn(ActionEvent actionEvent) {
        System.out.println("Active Inmate Button Clicked");
    }

    public void activeCaseBtn(ActionEvent actionEvent) {
        System.out.println("Active Case Button Clicked");
    }

    public void releseSoonBtn(ActionEvent actionEvent) {
        System.out.println("Release Soon Button Clicked");
    }

//    private void handleAddAction() {
//        // Implement logic for add action
//        System.out.println("Add action triggered (Ctrl + N)");
//    }
//
//    private void handleDeleteAction() {
//        // Implement logic for delete action
//        System.out.println("Delete action triggered (Ctrl + D)");
//    }
//
//    private void handleUpdateAction() {
//        // Implement logic for update action
//        System.out.println("Update action triggered (Ctrl + U)");
//    }
//
//    // You can also bind these actions to corresponding button click events
//    @FXML
//    private void handleAddButtonClick() {
//        handleAddAction();
//    }
//
//    @FXML
//    private void handleDeleteButtonClick() {
//        handleDeleteAction();
//    }
//
//    @FXML
//    private void handleUpdateButtonClick() {
//        handleUpdateAction();
//    }

}
