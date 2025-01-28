package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
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
public class TournamentPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TournamentTeam tournamentTeam;

    @ManyToOne
    private Player player;

    private int goals;

    public TournamentPlayer(TournamentTeam tournamentTeam, Player player, int goals) {

        this.tournamentTeam = tournamentTeam;
        this.player = player;
        this.goals = goals;
    }

    public TournamentPlayerGuiEntity toGuiEntity() {

        return TournamentPlayerGuiEntity.builder()
                .id(valueOf(id))
                .tournamentTeam(tournamentTeam.toGuiEntity())
                .player(player.toGuiEntity())
                .goals(valueOf(goals))
                .build();
    }
}