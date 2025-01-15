package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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
}