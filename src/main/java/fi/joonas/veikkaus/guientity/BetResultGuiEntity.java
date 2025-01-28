package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.jpaentity.BetResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;

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

    public BetResult toDbEntity() {

        try {
            return BetResult.builder()
                    .id(idValue(id))
                    .bet(bet.toDbEntity())
                    .game(game.toDbEntity())
                    .homeScore(homeScore)
                    .awayScore(awayScore)
                    .build();
        } catch (VeikkausConversionException e) {
            return BetResult.builder().build();
        }
    }
}