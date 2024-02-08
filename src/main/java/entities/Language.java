package entities;

public class Language {
    private int languageId;
    private String languageName;
    private String languageCode;

    public Language(int languageId, String languageName, String languageCode) {
        this.languageId = languageId;
        this.languageName = languageName;
        this.languageCode = languageCode;
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
