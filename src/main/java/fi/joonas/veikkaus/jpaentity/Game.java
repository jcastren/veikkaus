package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Tournament tournament;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private TournamentTeam homeTeam;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private TournamentTeam awayTeam;

    private int homeScore;
    private int awayScore;
    private Date gameDate;

    public Game(Tournament tournament, TournamentTeam homeTeam, TournamentTeam awayTeam, int homeScore, int awayScore, Date gameDate) {

        this.tournament = tournament;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.gameDate = gameDate;
    }
}