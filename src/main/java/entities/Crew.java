package entities;

public class Crew {
    private int filmID;
    private String title;
    private int personId;
    private String personName;
    private int depCode;
    private String depName;
    private String jobTitle;

    public Crew(int filmID, String title, int personId, String personName, int depCode, String depName,
                String jobTitle) {
        super();
        this.filmID = filmID;
        this.title = title;
        this.personId = personId;
        this.personName = personName;
        this.depCode = depCode;
        this.depName = depName;
        this.jobTitle = jobTitle;
    }
    public int getFilmID() {
        return filmID;
    }
    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
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
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
