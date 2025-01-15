package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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

    public BetResult(Bet bet, Game game, int homeScore, int awayScore) {

        this.bet = bet;
        this.game = game;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
}