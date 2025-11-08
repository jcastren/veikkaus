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
public class ScorerGuiEntity {

    @NotNull
    private Long id;

    @NotNull
    private TournamentPlayerGuiEntity tournamentPlayer;

    @NotNull
    private GameGuiEntity game;
}