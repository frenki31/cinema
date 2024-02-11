package entities;

public class MovieTrailer {
    private int filmID, trailerId;
    private String title;
    private String trailer;
    private String trailerLink;
    public MovieTrailer(int filmId, String title, int trailerId, String trailer, String trailerLink){
        setFilmID(filmId);
        setTitle(title);
        setTrailerId(trailerId);
        setTrailer(trailer);
    }
    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    public int getFilmID() {
        return filmID;
    }
    public String getTitle() {
        return title;
    }
    public String getTrailer() {
        return trailer;
    }
    public int getTrailerId(){
        return trailerId;
    }
    public void setTrailerId(int trailerId){
        this.trailerId = trailerId;
    }
    public String getTrailerLink(){
        return trailerLink;
    }
    public void setTrailerLink(String trailerLink){
        this.trailerLink = trailerLink;
    }
}
