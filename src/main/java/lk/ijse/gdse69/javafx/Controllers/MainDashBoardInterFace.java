package lk.ijse.gdse69.javafx.Controllers;

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

//    officer method //

    void onAddOfficerBtn() throws IOException;
    void onDeleteOfficerBtn() throws IOException;
    void onUpdateOfficerBtn() throws IOException;
    void onViewOfficerBtn() throws IOException;
    void officerProfileBtn() throws IOException;

//    sectionPage-Program method
    void onAddProgramBtn() throws IOException;
    void onDeleteProgramBtn() throws IOException;
    void onUpdateProgramBtn() throws IOException;
    void onViewProgramBtn() throws IOException;

//    sectionPage-Section method

    void onAddSectionBtn() throws IOException;
    void onDeleteSectionBtn() throws IOException;
    void onUpdateSectionBtn() throws IOException;
    void onViewSectionBtn() throws IOException;
    void sectionProfileBtn() throws IOException;

    //    visitorPage-Visitor method

    void onAddVisitorBtn() throws IOException;
    void onDeleteVisitorBtn() throws IOException;
    void onUpdateVisitorBtn() throws IOException;
    void onViewVisitorBtn() throws IOException;
    void visitorProfileBtn() throws IOException;
}
