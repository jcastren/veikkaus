package fi.joonas.veikkaus.guientity;

public class TournamentPlayerGuiEntity {

    private String id;
    private TournamentTeamGuiEntity tournamentTeam;
    private PlayerGuiEntity player;
    private String goals;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TournamentTeamGuiEntity getTournamentTeam() {
		return tournamentTeam;
	}
	public void setTournamentTeam(TournamentTeamGuiEntity tournamentTeam) {
		this.tournamentTeam = tournamentTeam;
	}
	public PlayerGuiEntity getPlayer() {
		return player;
	}
	public void setPlayer(PlayerGuiEntity player) {
		this.player = player;
	}
	public String getGoals() {
		return goals;
	}
	public void setGoals(String goals) {
		this.goals = goals;
	}
}

