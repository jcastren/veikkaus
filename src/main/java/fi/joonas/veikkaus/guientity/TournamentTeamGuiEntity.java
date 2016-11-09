package fi.joonas.veikkaus.guientity;

public class TournamentTeamGuiEntity {
	
	private String id;
    private TournamentGuiEntity tournament;
    private TeamGuiEntity team;
    
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
	public TeamGuiEntity getTeam() {
		return team;
	}
	public void setTeam(TeamGuiEntity team) {
		this.team = team;
	}
    
}
