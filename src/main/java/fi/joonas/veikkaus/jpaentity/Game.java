package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.GameGuiEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static fi.joonas.veikkaus.util.VeikkausUtil.getDateAsString;
import static java.lang.String.valueOf;

@Entity
@Data
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Tournament tournament;

    @NotNull
    @ManyToOne
    private TournamentTeam homeTeam;

    @NotNull
    @ManyToOne
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

    public GameGuiEntity toGuiEntity() {

        return GameGuiEntity.builder()
                .id(valueOf(id))
                .tournament(tournament.toGuiEntity())
                .homeTeam(homeTeam.toGuiEntity())
                .awayTeam(awayTeam.toGuiEntity())
                .homeScore(valueOf(homeScore))
                .awayScore(valueOf(awayScore))
                .gameDate(getDateAsString(gameDate))
                .build();
    }
}