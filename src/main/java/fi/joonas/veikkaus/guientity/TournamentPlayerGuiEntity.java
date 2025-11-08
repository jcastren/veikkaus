package fi.joonas.veikkaus.guientity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentPlayerGuiEntity {

    @NotNull
    private Long id;

    @NotNull
    private TournamentTeamGuiEntity tournamentTeam;

    @NotNull
    private PlayerGuiEntity player;

    @NotNull
    private int goals;
}