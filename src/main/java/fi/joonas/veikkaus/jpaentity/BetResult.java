package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.BetResultGuiEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.String.valueOf;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public BetResultGuiEntity toGuiEntity() {

        return BetResultGuiEntity.builder()
                .id(valueOf(id))
                .bet(bet.toGuiEntity())
                .game(game.toGuiEntity())
                .homeScore(homeScore)
                .awayScore(awayScore)
                .build();
    }
}