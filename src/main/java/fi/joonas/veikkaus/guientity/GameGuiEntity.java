package fi.joonas.veikkaus.guientity;

public class GameGuiEntity {
	
    private String id;
    private TournamentGuiEntity tournament;
    private TournamentTeamGuiEntity homeTeam;   
    private TournamentTeamGuiEntity awayTeam;
    private String homeScore;
	private String awayScore;
	private String gameDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TournamentGuiEntity getTournament() {
		return tournament;
	}
	public void setTournament(TournamentGuiEntity tournament) {
		this.tournament = tournament;
	}
	public TournamentTeamGuiEntity getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(TournamentTeamGuiEntity homeTeam) {
		this.homeTeam = homeTeam;
	}
	public TournamentTeamGuiEntity getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(TournamentTeamGuiEntity awayTeam) {
		this.awayTeam = awayTeam;
	}
	public String getHomeScore() {
		return homeScore;
	}
	public void setHomeScore(String homeScore) {
		this.homeScore = homeScore;
	}
	public String getAwayScore() {
		return awayScore;
	}
	public void setAwayScore(String awayScore) {
		this.awayScore = awayScore;
	}
	public String getGameDate() {
		return gameDate;
	}
	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}
}