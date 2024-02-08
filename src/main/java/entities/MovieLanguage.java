package entities;

public class MovieLanguage {
    private int filmID;
    private String title;
    private int languageId;
    private String languageName;
    private String languageCode;
    private int typeId;
    private String typeLanguage;

    public MovieLanguage(int filmID, String title, int languageId, String languageName, String languageCode, int typeId,
                         String type) {
        this.filmID = filmID;
        this.title = title;
        this.languageId = languageId;
        this.languageName = languageName;
        this.languageCode = languageCode;
        this.typeId = typeId;
        this.typeLanguage = type;
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
    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public String getTypeLanguage() {
        return typeLanguage;
    }
    public void setTypeLanguage(String type) {
        this.typeLanguage = type;
    }
}
