package lk.ijse.gdse69.javafx.Model;

public class InmateRecord {
    private String inmateRecordId;
    private String inmateId;
    private String sectionid;
    private String entryDate;
    private String releaseDate;
    private String crime;
    private String caseStatus;

    public InmateRecord() {
    }
    public InmateRecord(String inmateRecordId, String inmateId, String sectionid, String entryDate, String releaseDate, String crime, String caseStatus) {
        this.inmateRecordId = inmateRecordId;
        this.inmateId = inmateId;
        this.sectionid = sectionid;
        this.entryDate = entryDate;
        this.releaseDate = releaseDate;
        this.crime = crime;
        this.caseStatus = caseStatus;
    }

    public String getInmateRecordId() {
        return inmateRecordId;
    }

    public void setInmateRecordId(String inmateRecordId) {
        this.inmateRecordId = inmateRecordId;
    }

    public String getInmateId() {
        return inmateId;
    }

    public void setInmateId(String inmateId) {
        this.inmateId = inmateId;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    @Override
    public String toString() {
        return "InmateRecord{" +
                "inmateRecordId='" + inmateRecordId + '\'' +
                ", inmateId='" + inmateId + '\'' +
                ", sectionid='" + sectionid + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", crime='" + crime + '\'' +
                ", caseStatus='" + caseStatus + '\'' +
                '}';
    }
}
