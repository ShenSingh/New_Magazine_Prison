package lk.ijse.gdse69.javafx.Model;

public class Inmate {
    private String inmateId;
    private String inmateFirstName;
    private String inmateLastName;
    private String inmateDOB;
    private String inmateNIC;
    private String gender;
    private String inmateAddress;
    private String status;

    public Inmate() {
    }

    public Inmate(String inmateId, String inmateFirstName, String inmateLastName, String inmateDOB, String inmateNIC,
                  String gender , String inmateAddress, String status) {
        this.inmateId = inmateId;
        this.inmateFirstName = inmateFirstName;
        this.inmateLastName = inmateLastName;
        this.inmateDOB = inmateDOB;
        this.inmateNIC = inmateNIC;
        this.gender = gender;
        this.inmateAddress = inmateAddress;
        this.status = status;
    }

    public String getInmateId() {
        return inmateId;
    }
    public void setInmateId(String inmateId) {
        this.inmateId = inmateId;
    }

    public String getInmateFirstName() {
        return inmateFirstName;
    }
    public void setInmateFirstName(String inmateFirstName) {
        this.inmateFirstName = inmateFirstName;
    }
    public String getInmateLastName() {
        return inmateLastName;
    }

    public void setInmateLastName(String inmateLastName) {
        this.inmateLastName = inmateLastName;
    }

    public String getInmateDOB() {
        return inmateDOB;
    }

    public void setInmateDOB(String inmateDOB) {
        this.inmateDOB = inmateDOB;
    }

    public String getInmateNIC() {
        return inmateNIC;
    }

    public void setInmateNIC(String inmateNIC) {
        this.inmateNIC = inmateNIC;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getInmateAddress() {
        return inmateAddress;
    }

    public void setInmateAddress(String inmateAddress) {
        this.inmateAddress = inmateAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Inmate{" +
                "inmateId='" + inmateId + '\'' +
                ", inmateFirstName='" + inmateFirstName + '\'' +
                ", inmateLastName='" + inmateLastName + '\'' +
                ", inmateDOB='" + inmateDOB + '\'' +
                ", inmateNIC='" + inmateNIC + '\'' +
                ", gender='" + gender + '\'' +
                ", inmateAddress='" + inmateAddress + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
