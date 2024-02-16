package entities;

public class MovieGenre {
    private int filmID;
    private String title;
    private String genreCategory;

    public MovieGenre(int filmID, String title, String genreCategory) {
        this.filmID = filmID;
        this.title = title;
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
    public String getGenreCategory() {
        return genreCategory;
    }
    public void setGenreCategory(String genreCategory) {
        this.genreCategory = genreCategory;
    }
}
