package fi.joonas.veikkaus.guientity;

import lombok.Data;

@Data
public class GameGuiEntity {

    private String id;
    private TournamentGuiEntity tournament;
    private TournamentTeamGuiEntity homeTeam;
    private TournamentTeamGuiEntity awayTeam;
    private String homeScore;
    private String awayScore;
    private String gameDate;
}