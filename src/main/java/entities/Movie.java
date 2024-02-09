package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Movie {
    private int filmID;
    private String title;
    private LocalTime duration;
    private String cover;
    private String description;
    private LocalDate releaseDate;
    private double budget;
    private double revenue;
    private double rating;
    private int nrOfRatings;
    private String status;
    private int totalRating;
    private int adminId;
    private String username;
    public Movie(int filmID, String title, double budget, String description, double revenue,
                 LocalDate releaseDate, LocalTime duration, String status, double rating,
                 int nrOfRatings, String cover,int totalRating, int adminId) {
        setFilmID(filmID);
        setTitle(title);
        setBudget(budget);
        setDescription(description);
        setRevenue(revenue);
        setReleaseDate(releaseDate);
        setDuration(duration);
        setStatus(status);
        setRating(rating);
        setNrOfRatings(nrOfRatings);
        setCover(cover);
        setAdminId(adminId);
        setTotalRating(totalRating);
    }

    public Movie(String title, LocalTime duration, LocalDate release, String overview, String cover, double rating) {
        setTitle(title);
        setReleaseDate(release);
        setDuration(duration);
        setDescription(overview);
        setCover(cover);
        setRating(rating);
    }

    public Movie(int id, String title) {
        setFilmID(id);
        setTitle(title);
    }
    /**
     * Constructor for movie class
     * @param title
     * @param cover
     * @param releaseDate
     **/
    public Movie(String title, String cover, LocalDate releaseDate) {
        setTitle(title);
        setCover(cover);
        setReleaseDate(releaseDate);
    }
    /**
     * Getters and setters
     * @return
     */
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getFilmID() {
        return filmID;
    }
    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    public LocalTime getDuration() {
        return duration;
    }
    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }
    public String getCover() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public double getBudget() {
        return budget;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public double getRevenue() {
        return revenue;
    }
    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public int getNrOfRatings() {
        return nrOfRatings;
    }
    public void setNrOfRatings(int nrOfRatings) {
        this.nrOfRatings = nrOfRatings;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getTotalRating(){
        return totalRating;
    }
    public void setTotalRating(int totalRating){
        this.totalRating = totalRating;
    }
}
