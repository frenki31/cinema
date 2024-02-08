package entities;

public class Person {
    private int actorId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String type;

    public Person(int id, String fname, String mname, String lname, String gender, String type) {
        setActorId(id);
        setFirstName(fname);
        setMiddleName(mname);
        setLastName(lname);
        setGender(gender);
        setType(type);
    }
    public int getActorId() {
        return actorId;
    }
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return firstName + " "+middleName+" "+lastName;
    }
}
