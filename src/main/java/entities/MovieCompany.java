package entities;

public class MovieCompany {
    private int filmID;
    private String title;
    private int companyCode;
    private String companyName;

    public MovieCompany(int id, String title,int code, String company) {
        setFilmID(id);
        setTitle(title);
        setCompanyCode(code);
        setCompanyName(company);
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
    public int getCompanyCode() {
        return companyCode;
    }
    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
