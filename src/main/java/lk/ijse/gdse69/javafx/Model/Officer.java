package lk.ijse.gdse69.javafx.Model;

public class Officer {
    private String officerId;
    private String officerFirstName;
    private String officerLastName;
    private String officerDOB;
    private String officerNIC;
    private String gender;
    private String officerAddress;
    private String officerEmail;
    private String officerNumber;
    private String position;
    private String sectionId;
    private double salary;

    public Officer() {
    }

    public Officer(String officerId, String officerFirstName, String officerLastName, String officerDOB, String officerNIC,
                   String gender, String officerAddress, String officerEmail, String officerNumber, String position, String sectionId, double salary) {
        this.officerId = officerId;
        this.officerFirstName = officerFirstName;
        this.officerLastName = officerLastName;
        this.officerDOB = officerDOB;
        this.officerNIC = officerNIC;
        this.gender = gender;
        this.officerAddress = officerAddress;
        this.officerEmail = officerEmail;
        this.officerNumber = officerNumber;
        this.position = position;
        this.sectionId = sectionId;
        this.salary = salary;
    }

    public String getOfficerId() {
        return officerId;
    }
    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }
    public String getOfficerFirstName() {
        return officerFirstName;
    }
    public void setOfficerFirstName(String officerFirstName) {
        this.officerFirstName = officerFirstName;
    }

    public String getOfficerLastName() {
        return officerLastName;
    }
    public void setOfficerLastName(String officerLastName) {
        this.officerLastName = officerLastName;
    }
    public String getOfficerDOB() {
        return officerDOB;
    }
    public void setOfficerDOB(String officerDOB) {
        this.officerDOB = officerDOB;
    }
    public String getOfficerNIC() {
        return officerNIC;
    }
    public void setOfficerNIC(String officerNIC) {
        this.officerNIC = officerNIC;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public String getOfficerAddress() {
        return officerAddress;
    }
    public void setOfficerAddress(String officerAddress) {
        this.officerAddress = officerAddress;
    }
    public String getOfficerEmail() {
        return officerEmail;
    }
    public void setOfficerEmail(String officerEmail) {
        this.officerEmail = officerEmail;
    }
    public String getOfficerNumber() {
        return officerNumber;
    }
    public void setOfficerNumber(String officerNumber) {
        this.officerNumber = officerNumber;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Officer{" +
                "officerId='" + officerId + '\'' +
                ", officerFirstName='" + officerFirstName + '\'' +
                ", officerLastName='" + officerLastName + '\'' +
                ", officerDOB='" + officerDOB + '\'' +
                ", officerNIC='" + officerNIC + '\'' +
                ", gender='" + gender + '\'' +
                ", officerAddress='" + officerAddress + '\'' +
                ", officerEmail='" + officerEmail + '\'' +
                ", officerNumber='" + officerNumber + '\'' +
                ", position='" + position + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
