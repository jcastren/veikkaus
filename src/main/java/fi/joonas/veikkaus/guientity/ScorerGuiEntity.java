package fi.joonas.veikkaus.guientity;

public class ScorerGuiEntity {
	private String id;
	private TournamentPlayerGuiEntity tournamentPlayer;
	private GameGuiEntity game;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TournamentPlayerGuiEntity getTournamentPlayer() {
		return tournamentPlayer;
	}

	public void setTournamentPlayer(TournamentPlayerGuiEntity tournamentPlayer) {
		this.tournamentPlayer = tournamentPlayer;
	}

	public GameGuiEntity getGame() {
		return game;
	}

	public void setGame(GameGuiEntity game) {
		this.game = game;
	}

}
