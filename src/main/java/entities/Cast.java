package entities;

public class Cast {
    private int filmID;
    private String title;
    private int actorId;
    private String actorName;
    private String character;
    private int order;

    public Cast(String actorName, String character, int order) {
        setActorName(actorName);
        setCharacter(character);
        setOrder(order);
    }

    public Cast(int filmID, String title, int actorId, String actorName,String character,
                int order) {
        this.filmID = filmID;
        this.title = title;
        this.actorId = actorId;
        this.actorName = actorName;
        this.character = character;
        this.order = order;
    }
    public String getCharacter() {
        return character;
    }
    public void setCharacter(String character) {
        this.character = character;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
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
    public int getActorId() {
        return actorId;
    }
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
    public String getActorName() {
        return actorName;
    }
    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
}
