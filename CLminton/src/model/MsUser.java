package model;

public class MsUser {
	
	private String id, email, password, gender, nationality, role;
	private int age ;
	private static int currentUserId;

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public MsUser(String id, String email, String password, String gender, String nationality, String role, int age) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.nationality = nationality;
		this.role = role;
		this.age = age;
	}

	public void setUsername(String string) {
		// TODO Auto-generated method stub
		
	}

	public Object getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
