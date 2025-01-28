package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentTeamGuiEntity {

    private String id;
    private TournamentGuiEntity tournament;
    private TeamGuiEntity team;

    public TournamentTeam toDbEntity() {

        return TournamentTeam.builder()
                .id(idValue(id))
                .tournament(tournament.toDbEntity())
                .team(team.toDbEntity())
                .build();
    }
}

