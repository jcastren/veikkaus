package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.*;

@Entity
public class BetResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Bet bet;

    @ManyToOne
    private Game game;

    private int homeScore;
    private int awayScore;

    public BetResult() {
    }

    public BetResult(Bet bet, Game game, int homeScore, int awayScore) {
        this.bet = bet;
        this.game = game;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
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

