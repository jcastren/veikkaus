package fi.joonas.veikkaus.guientity;

import lombok.Data;

@Data
public class TournamentTeamGuiEntity {

    private Long id;
    private TournamentGuiEntity tournament;
    private TeamGuiEntity team;
}