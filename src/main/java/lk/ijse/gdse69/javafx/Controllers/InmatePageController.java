package lk.ijse.gdse69.javafx.Controllers;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class InmatePageController extends MainDashBoard{
    public ImageView sirLankaLogo;
    public AnchorPane mainAnchorPage;

    public void initialize(){

        System.out.println("Inmate Page");

        ////////////         You can bind the actions to the corresponding key combinations          /////////////////////////////////////

        //addButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN), this::handleAddAction);
        //addButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN), this::handleDeleteAction);
        //addButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.U, KeyCodeCombination.CONTROL_DOWN), this::handleUpdateAction);
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
