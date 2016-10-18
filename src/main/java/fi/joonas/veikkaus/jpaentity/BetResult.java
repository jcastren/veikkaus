package fi.joonas.veikkaus.jpaentity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fi.joonas.veikkaus.util.Score;

@Entity
@Table
public class BetResult {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private User user;
    
    private Score score;
    
    @ManyToOne
    private Match match;

    public BetResult() {}
    
    public BetResult(Long id) {
    	this.id = id;
    }

	public BetResult(User user, Score score, Match match) {
		super();
		this.user = user;
		this.score = score;
		this.match = match;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	

}

