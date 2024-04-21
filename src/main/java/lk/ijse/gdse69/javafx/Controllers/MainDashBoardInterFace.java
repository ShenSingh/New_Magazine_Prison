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

//    inmate method //

void onAddInmateBtn() throws IOException;
void onDeleteInmateBtn() throws IOException;
void onUpdateInmateBtn() throws IOException;
void onViewInmateBtn() throws IOException;
void inmateProfileBtn() throws IOException;

void addRecordBtn() throws IOException;
void deleteRecordBtn() throws IOException;
void updateRecordBtn() throws IOException;
void viewRecordBtn() throws IOException;
}
