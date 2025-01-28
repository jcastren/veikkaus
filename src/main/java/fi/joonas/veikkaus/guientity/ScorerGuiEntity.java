package fi.joonas.veikkaus.guientity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScorerGuiEntity {

    private String id;
    private TournamentPlayerGuiEntity tournamentPlayer;
    private GameGuiEntity game;
}