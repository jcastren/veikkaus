package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @JoinColumn(nullable = false)
    private Bet bet;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Game game;

    @NotNull
    @Column(nullable = false, columnDefinition = "int default 0")
    private int homeScore;

    @NotNull
    @Column(nullable = false, columnDefinition = "int default 0")
    private int awayScore;

    public BetResult(Bet bet, Game game, int homeScore, int awayScore) {
        this.bet = bet;
        this.game = game;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
}