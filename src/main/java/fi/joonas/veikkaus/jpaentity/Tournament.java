package fi.joonas.veikkaus.jpaentity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Tournament {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String name;
    private int year;
    
    public Tournament() {}
    
	public Tournament(String name, int year) {
		this.name = name;
		this.year = year;
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}

