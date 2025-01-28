package fi.joonas.veikkaus.guientity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameGuiEntity {

    private String id;
    private TournamentGuiEntity tournament;
    private TournamentTeamGuiEntity homeTeam;
    private TournamentTeamGuiEntity awayTeam;
    private String homeScore;
    private String awayScore;
    private String gameDate;
}