package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.ScorerGuiEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.String.valueOf;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scorer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private TournamentPlayer tournamentPlayer;

    @ManyToOne
    private Game game;

    public Scorer(TournamentPlayer tournamentPlayer, Game game) {

        this.tournamentPlayer = tournamentPlayer;
        this.game = game;
    }

    public ScorerGuiEntity toGuiEntity() {

        return ScorerGuiEntity.builder()
                .id(valueOf(id))
                .tournamentPlayer(tournamentPlayer.toGuiEntity())
                .game(game.toGuiEntity())
                .build();
    }
}