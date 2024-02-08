package entities;

public class MovieCountry {
    private int filmID;
    private String title;
    private int countryCode;
    private String countryName;

    public MovieCountry(int id, String title,int code, String country) {
        setFilmID(id);
        setTitle(title);
        setCountryCode(code);
        setCountryName(country);
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

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
