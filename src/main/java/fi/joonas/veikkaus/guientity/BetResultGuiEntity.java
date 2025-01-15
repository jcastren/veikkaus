package fi.joonas.veikkaus.guientity;

import lombok.Data;

@Data
public class BetResultGuiEntity {

    private String id;
    private BetGuiEntity bet;
    private GameGuiEntity game;
    private int homeScore;
    private int awayScore;
}