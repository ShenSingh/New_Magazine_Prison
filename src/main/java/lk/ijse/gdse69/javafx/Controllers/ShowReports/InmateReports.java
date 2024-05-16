package lk.ijse.gdse69.javafx.Controllers.ShowReports;

import lk.ijse.gdse69.javafx.db.DbConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;

public class InmateReports {
    public static void getSelection(String selection){
        if (selection.equals("All")) {
            try {
                JasperDesign design = JRXmlLoader.load("src/main/resources/Reports/All_Inmate_Report.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(design);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
