package fi.joonas.veikkaus.guientity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentPlayerGuiEntity {

    private String id;
    private TournamentTeamGuiEntity tournamentTeam;
    private PlayerGuiEntity player;
    private String goals;
}