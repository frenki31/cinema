package entities;

public class Department {
    private int depCode;
    private String depName;

    public Department(int depCode, String depName) {
        super();
        this.depCode = depCode;
        this.depName = depName;
    }
    public int getDepCode() {
        return depCode;
    }
    public void setDepCode(int depCode) {
        this.depCode = depCode;
    }
    public String getDepName() {
        return depName;
    }
    public void setDepName(String depName) {
        this.depName = depName;
    }

}
