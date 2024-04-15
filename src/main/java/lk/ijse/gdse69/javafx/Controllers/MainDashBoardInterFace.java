package lk.ijse.gdse69.javafx.Controllers;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public interface MainDashBoardInterFace{
    void onInmate() throws IOException;
    void onOfficer() throws IOException;
    void onVisitor() throws IOException;
    void onSection() throws IOException;
    void onMany() throws IOException;
    void onSetting() throws IOException;
    void onDashBord() throws IOException;
    void onLogOut();
    void createStage(String path) throws IOException;
    void systemCloseBtn();
    void miniHideBtn();
}
