package lk.ijse.gdse69.javafx.Model;

public class IncidentRelatedInmate {
    private int number;
    private String incidentID;
    private String inmateID;

    public IncidentRelatedInmate() {
    }

    public IncidentRelatedInmate(int number, String incidentID, String inmateID) {
        this.number = number;
        this.incidentID = incidentID;
        this.inmateID = inmateID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getIncidentID() {
        return incidentID;
    }

    public void setIncidentID(String incidentID) {
        this.incidentID = incidentID;
    }

    public String getInmateID() {
        return inmateID;
    }

    public void setInmateID(String inmateID) {
        this.inmateID = inmateID;
    }

    @Override
    public String toString() {
        return "IncidentRelatedInmate{" +
                "number=" + number +
                ", incidentID='" + incidentID + '\'' +
                ", inmateID='" + inmateID + '\'' +
                '}';
    }
}
