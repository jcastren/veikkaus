package fi.joonas.veikkaus.jpaentity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private String email;

    @NotNull
    private String name;

    private String password;
    
    @ManyToOne
    private UserRole userRole;

    public User() {}
    
    public User(String email, String name, String password, UserRole userRole) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.userRole = userRole;
    }
    
    public User(Long id) {
    	this.id = id;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}

