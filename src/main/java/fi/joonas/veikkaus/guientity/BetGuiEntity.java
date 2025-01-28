package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.jpaentity.Bet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetGuiEntity {

    private String id;
    private UserGuiEntity user;
    private TournamentGuiEntity tournament;
    private StatusGuiEntity status;

    public Bet toDbEntity() {

        return Bet.builder()
                .id(idValue(id))
                .user(user.toDbEntity())
                .tournament(tournament.toDbEntity())
                .status(status.toDbEntity())
                .build();
    }
}