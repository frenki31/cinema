package entities;

public class Country {
    private int countryCode;
    private String countryIso;
    private String countryName;

    public Country(int code, String iso, String name) {
        setCountryCode(code);
        setCountryIso(iso);
        setCountryName(name);
    }
    public Country(int code, String name) {
        setCountryCode(code);
        setCountryName(name);
    }
    public int getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }
    public String getCountryIso() {
        return countryIso;
    }
    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
