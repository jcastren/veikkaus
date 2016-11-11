package fi.joonas.veikkaus.guientity;

public class UserGuiEntity {
	
	private String id;
    private String email;
    private String name;
    private String password;
    private UserRoleGuiEntity userRole;
    
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRoleGuiEntity getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoleGuiEntity userRole) {
		this.userRole = userRole;
	}
}