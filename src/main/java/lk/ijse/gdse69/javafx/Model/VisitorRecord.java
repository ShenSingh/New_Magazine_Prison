package lk.ijse.gdse69.javafx.Model;

public class VisitorRecord {
    private String visitorRecordId;
    private String visitorId;
    private String inmateId;
    private String visitDate;
    private String visitTime;

    public VisitorRecord() {
    }

    public VisitorRecord(String visitorRecordId, String visitorId, String inmateId, String visitDate, String visitTime) {
        this.visitorRecordId = visitorRecordId;
        this.visitorId = visitorId;
        this.inmateId = inmateId;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
    }

    public String getVisitorRecordId() {
        return visitorRecordId;
    }

    public void setVisitorRecordId(String visitorRecordId) {
        this.visitorRecordId = visitorRecordId;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getInmateId() {
        return inmateId;
    }

    public void setInmateId(String inmateId) {
        this.inmateId = inmateId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    @Override
    public String toString() {
        return "VisitorRecord{" +
                "visitorRecordId='" + visitorRecordId + '\'' +
                ", visitorId='" + visitorId + '\'' +
                ", inmateId='" + inmateId + '\'' +
                ", visitDate='" + visitDate + '\'' +
                ", visitTime='" + visitTime + '\'' +
                '}';
    }
}
