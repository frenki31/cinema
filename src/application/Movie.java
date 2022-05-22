package application;

public class Movie {
	private int filmID;
	private String title;
	private String duration;
	private String cover;
	private String trailer;
	private String description;
	private int releaseYear;
	
	public Movie(int filmID, String title, String duration, String cover, String trailer, String description,
			int releaseYear) {
		this.filmID = filmID;
		this.title = title;
		this.duration = duration;
		this.cover = cover;
		this.trailer = trailer;
		this.description = description;
		this.releaseYear = releaseYear;
	}
	public Movie(String cover) {
		setCover(cover);
	}
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
	public int getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
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
}
