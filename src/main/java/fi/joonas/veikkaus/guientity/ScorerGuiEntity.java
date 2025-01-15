package fi.joonas.veikkaus.guientity;

import lombok.Data;

@Data
public class ScorerGuiEntity {

    private String id;
    private TournamentPlayerGuiEntity tournamentPlayer;
    private GameGuiEntity game;
}