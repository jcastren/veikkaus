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

public class BetGuiEntity {

    @NotNull
    private Long id;

    @NotNull
    private UserGuiEntity user;

    @NotNull
    private TournamentGuiEntity tournament;

    @NotNull
    private StatusGuiEntity status;
}