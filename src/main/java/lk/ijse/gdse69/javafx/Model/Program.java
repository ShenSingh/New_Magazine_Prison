package lk.ijse.gdse69.javafx.Model;

public class Program {
    private String programId;
    private String programName;
    private String sectionId;
    private String programDate;
    private String programTime;
    private String description;

    public Program(){

    }

    public Program(String programId, String programName, String sectionId, String programDate, String programTime, String description) {
        this.programId = programId;
        this.programName = programName;
        this.sectionId = sectionId;
        this.programDate = programDate;
        this.programTime = programTime;
        this.description = description;
    }

    public String getProgramId() {
        return programId;
    }
    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }
    public void setProgramName(String programName) {
        this.programName = programName;
    }
    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public String getProgramDate() {
        return programDate;
    }
    public void setProgramDate(String programDate) {
        this.programDate = programDate;
    }
    public String getProgramTime() {
        return programTime;
    }
    public void setProgramTime(String programTime) {
        this.programTime = programTime;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programId='" + programId + '\'' +
                ", programName='" + programName + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", programDate='" + programDate + '\'' +
                ", programTime='" + programTime + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
