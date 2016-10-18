package fi.joonas.veikkaus.jpaentity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public abstract class BaseEntity<id extends Serializable> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    public BaseEntity(Long id) {
    	this.id = id;
    }

	public Long getId() {
		return id;
	}

}

