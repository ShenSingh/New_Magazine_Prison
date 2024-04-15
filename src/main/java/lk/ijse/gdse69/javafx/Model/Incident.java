package lk.ijse.gdse69.javafx.Model;

public class Incident {
    private String incidentId;
    private String sectionId;
    private String incidentType;
    private String incidentDate;
    private String incidentTime;
    private String description;

    public Incident() {
    }
    public Incident(String incidentId, String sectionId, String incidentType, String incidentDate, String incidentTime, String description) {
        this.incidentId = incidentId;
        this.sectionId = sectionId;
        this.incidentType = incidentType;
        this.incidentDate = incidentDate;
        this.incidentTime = incidentTime;
        this.description = description;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(String incidentTime) {
        this.incidentTime = incidentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "incidentId='" + incidentId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", incidentType='" + incidentType + '\'' +
                ", incidentDate='" + incidentDate + '\'' +
                ", incidentTime='" + incidentTime + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
