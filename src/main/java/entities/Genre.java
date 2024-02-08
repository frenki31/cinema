package entities;

public class Genre {
    private int id;
    private String category;

    public Genre(String category) {
        setCategory(category);
    }
    public Genre(int id,String type) {
        setId(id);
        setCategory(type);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
