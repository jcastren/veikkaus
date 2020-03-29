package fi.joonas.veikkaus.jpaentity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    @OneToOne
	private Tournament tournament;

    @NotNull
    @OneToOne
    private TournamentTeam homeTeam;

    @NotNull
    @OneToOne
    private TournamentTeam awayTeam;
    
    private int homeScore;
	private int awayScore;
	private Date gameDate;
    
    public Game() {}

	public Game(Tournament tournament, TournamentTeam homeTeam, TournamentTeam awayTeam, int homeScore, int awayScore, Date gameDate) {
		this.tournament = tournament;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		this.gameDate = gameDate;
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
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
