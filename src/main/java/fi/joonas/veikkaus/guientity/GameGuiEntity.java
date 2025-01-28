package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.exception.VeikkausConversionException;
import fi.joonas.veikkaus.jpaentity.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

import static fi.joonas.veikkaus.util.VeikkausUtil.getStringAsDate;
import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;
import static java.lang.Integer.parseInt;

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

    public Game toDbEntity() throws VeikkausConversionException {

        try {
            return Game.builder()
                    .id(idValue(id))
                    .homeScore(parseInt(homeScore))
                    .awayScore(parseInt(awayScore))
                    .gameDate(getStringAsDate(gameDate))
                    .tournament(tournament.toDbEntity())
                    .homeTeam(homeTeam.toDbEntity())
                    .awayTeam(awayTeam.toDbEntity())
                    .build();
        } catch (ParseException parseException) {
            throw new VeikkausConversionException("Error while parsing date: %s".formatted(gameDate), parseException);
        }
    }
}