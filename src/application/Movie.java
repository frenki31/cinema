package application;

public class Movie {
	private int filmID;
	private String title;
	private String duration;
	private String cover;
	private String trailer;
	private String description;
	private String releaseYear;
	private String genre;
	private String movieLink;
	
	public Movie(int filmID, String title, String duration, String cover, String trailer, String description,
			String releaseYear, String genre, String movieLink) {
		this.filmID = filmID;
		this.title = title;
		this.duration = duration;
		this.cover = cover;
		this.trailer = trailer;
		this.description = description;
		this.releaseYear = releaseYear;
		this.genre = genre;
		this.movieLink = movieLink;
	}
	/**
	 * Constructor for movie class
	 * @param title
	 * @param cover
	 * @param releaseYear
	 * @param genre
	 */
	public Movie(String title, String cover, String releaseYear,String genre) {
		setTitle(title);
		setCover(cover);
		setReleaseYear(releaseYear);
		setGenre(genre);
	}
	
	public Movie(String movieLink) {
		setMovieLink(movieLink);
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
	public String getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(String releaseYear2) {
		this.releaseYear = releaseYear2;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getTrailer() {
		return trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getMovieLink() {
		return movieLink;
	}
	public void setMovieLink(String movieLink) {
		this.movieLink = movieLink;
	}
}
