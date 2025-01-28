package fi.joonas.veikkaus.guientity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetGuiEntity {

    private String id;
    private UserGuiEntity user;
    private TournamentGuiEntity tournament;
    private StatusGuiEntity status;
}