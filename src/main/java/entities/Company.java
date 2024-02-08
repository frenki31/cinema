package entities;

public class Company {
    private int companyCode;
    private String companyName;
    private double companyRevenue;

    public Company(int companyCode, String companyName, double companyRevenue) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        setCompanyRevenue(companyRevenue);
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
    public double getCompanyRevenue() {
        return companyRevenue;
    }
    public void setCompanyRevenue(double companyRevenue){
        this.companyRevenue = companyRevenue;
    }
}
