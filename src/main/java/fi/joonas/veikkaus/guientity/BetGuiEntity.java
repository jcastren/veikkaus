package fi.joonas.veikkaus.guientity;

import lombok.Data;

@Data
public class BetGuiEntity {

    private String id;
    private UserGuiEntity user;
    private TournamentGuiEntity tournament;
    private StatusGuiEntity status;
}