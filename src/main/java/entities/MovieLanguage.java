package entities;

public class MovieLanguage {
    private int filmID;
    private String title;
    private int languageId;
    private String languageName;
    private String languageCode;

    public MovieLanguage(int filmID, String title, int languageId, String languageName, String languageCode) {
        this.filmID = filmID;
        this.title = title;
        this.languageId = languageId;
        this.languageName = languageName;
        this.languageCode = languageCode;
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
    public int getLanguageId() {
        return languageId;
    }
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
    public String getLanguageName() {
        return languageName;
    }
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
    public String getLanguageCode() {
        return languageCode;
    }
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
