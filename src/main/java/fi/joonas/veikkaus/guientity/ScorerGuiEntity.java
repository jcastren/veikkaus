package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.jpaentity.Scorer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScorerGuiEntity {

    private String id;
    private TournamentPlayerGuiEntity tournamentPlayer;
    private GameGuiEntity game;

    public Scorer toDbEntity() throws VeikkausConversionException {

        return Scorer.builder()
                .id(idValue(id))
                .tournamentPlayer(tournamentPlayer.toDbEntity())
                .game(game.toDbEntity())
                .build();
    }
}