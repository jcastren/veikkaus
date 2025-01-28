package fi.joonas.veikkaus.guientity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetResultGuiEntity {

    private String id;
    private BetGuiEntity bet;
    private GameGuiEntity game;
    private int homeScore;
    private int awayScore;
}