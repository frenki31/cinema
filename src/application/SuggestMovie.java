package application;

public class SuggestMovie {
	private int id;
	private String title;
	private String director;
	private String year;
	private String genre;
	/**
	 * Constructor of suggest movie class
	 * @param id
	 * @param title
	 * @param director
	 * @param year
	 * @param genre
	 */
	public SuggestMovie(int id, String title, String director, String year, String genre) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.year = year;
		this.genre = genre;
	}
	/**
	 * Getters and setters
	 * @return
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
}
