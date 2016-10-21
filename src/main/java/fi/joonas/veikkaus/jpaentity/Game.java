package fi.joonas.veikkaus.jpaentity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private TournamentTeam homeTeam;
    
    @OneToOne
    private TournamentTeam awayTeam;
    
    private int homeScore;
	private int awayScore;
	private Date gameDate;
    
    public Game() {}

	public Game(TournamentTeam homeTeam, TournamentTeam awayTeam, int homeScore, int awayScore, Date gameDate) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		this.gameDate = gameDate;
	}	

	public Long getId() {
		return id;
	}

	public TournamentTeam getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(TournamentTeam homeTeam) {
		this.homeTeam = homeTeam;
	}

	public TournamentTeam getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(TournamentTeam awayTeam) {
		this.awayTeam = awayTeam;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public int getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

}
