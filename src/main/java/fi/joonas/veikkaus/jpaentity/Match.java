package fi.joonas.veikkaus.jpaentity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import fi.joonas.veikkaus.util.Score;

@Entity
@Table
public class Match {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private TournamentTeam homeTeam;
    
    @OneToOne
    private TournamentTeam awayTeam;
    
    private Score score;
    
    public Match() {}
    
    public Match(Long id) {
    	this.id = id;
    }

	public Match(TournamentTeam homeTeam, TournamentTeam awayTeam, Score score) {
		super();
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

}

