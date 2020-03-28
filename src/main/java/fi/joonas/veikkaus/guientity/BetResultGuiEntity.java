package fi.joonas.veikkaus.guientity;

public class BetResultGuiEntity {

	private String id;
    private BetGuiEntity bet;
    private GameGuiEntity game;
    private int homeScore;
    private int awayScore;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BetGuiEntity getBet() {
		return bet;
	}

	public void setBet(BetGuiEntity bet) {
		this.bet = bet;
	}

	public GameGuiEntity getGame() {
		return game;
	}

	public void setGame(GameGuiEntity game) {
		this.game = game;
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
}