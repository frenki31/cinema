package entities;

public class MovieGenre {
    private int filmID;
    private String title;
    private int genreID;
    private String genreCategory;

    public MovieGenre(int filmID, String title, int genreID, String genreCategory) {
        this.filmID = filmID;
        this.title = title;
        this.genreID = genreID;
        this.genreCategory = genreCategory;
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
    public int getGenreID() {
        return genreID;
    }
    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }
    public String getGenreCategory() {
        return genreCategory;
    }
    public void setGenreCategory(String genreCategory) {
        this.genreCategory = genreCategory;
    }
}
