package fi.joonas.veikkaus.guientity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameGuiEntity {

    @NotNull
    private Long id;

    @NotNull
    private TournamentGuiEntity tournament;

    @NotNull
    private TournamentTeamGuiEntity homeTeam;

    @NotNull
    private TournamentTeamGuiEntity awayTeam;

    @NotNull
    private int homeScore;

    @NotNull
    private int awayScore;

    @NotNull
    private String gameDate;
}