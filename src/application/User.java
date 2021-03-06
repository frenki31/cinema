package application;

public class User {
	private int id;
	private String name;
	private String email;
	private String phoneNo;
	private String password;
	/**
	 * Constructor for user class
	 * @param id
	 * @param name
	 * @param email
	 * @param phoneNo
	 * @param password
	 */
	public User(int id,String name, String email, String phoneNo, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNo = phoneNo;
		this.password = password;
	}
	/**
	 * Getters and setters
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
