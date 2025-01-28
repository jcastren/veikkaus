package fi.joonas.veikkaus.guientity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentTeamGuiEntity {

    private String id;
    private TournamentGuiEntity tournament;
    private TeamGuiEntity team;
}