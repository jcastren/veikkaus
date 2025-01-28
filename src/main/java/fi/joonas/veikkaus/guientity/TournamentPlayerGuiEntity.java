package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;
import static java.lang.Integer.parseInt;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentPlayerGuiEntity {

    private String id;
    private TournamentTeamGuiEntity tournamentTeam;
    private PlayerGuiEntity player;
    private String goals;

    public TournamentPlayer toDbEntity() {

        return TournamentPlayer.builder()
                .id(idValue(id))
                .tournamentTeam(tournamentTeam.toDbEntity())
                .player(player.toDbEntity())
                .goals(parseInt(goals))
                .build();
    }
}