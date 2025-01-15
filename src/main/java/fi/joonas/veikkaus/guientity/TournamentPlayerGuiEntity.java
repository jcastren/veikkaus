package fi.joonas.veikkaus.guientity;

import lombok.Data;

@Data
public class TournamentPlayerGuiEntity {

    private String id;
    private TournamentTeamGuiEntity tournamentTeam;
    private PlayerGuiEntity player;
    private String goals;
}