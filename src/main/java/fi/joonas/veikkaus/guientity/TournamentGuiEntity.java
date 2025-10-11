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
public class TournamentGuiEntity {
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String year;
}