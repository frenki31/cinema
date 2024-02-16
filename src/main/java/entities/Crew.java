package entities;

public class Crew {
    private int filmID;
    private String title;
    private int personId;
    private String personName;
    private String jobTitle;

    public Crew(int filmID, String title, int personId, String personName, String jobTitle) {
        super();
        this.filmID = filmID;
        this.title = title;
        this.personId = personId;
        this.personName = personName;
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
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
