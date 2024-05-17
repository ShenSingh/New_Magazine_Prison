package lk.ijse.gdse69.javafx.Controllers.ShowReports;

import lk.ijse.gdse69.javafx.db.DbConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OfficerReports {

    public static void getSelection(String selection){

        if (selection.equals("All")) { // Use equals method to compare strings
            try {
                JasperDesign design = JRXmlLoader.load("src/main/resources/Reports/Waves_Landscape.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(design);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            String id = selection;

            JasperDesign design = null;

            try {
                design = JRXmlLoader.load("src/main/resources/Reports/Officer_Report_Profile.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(design);

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("officerId", id);

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
