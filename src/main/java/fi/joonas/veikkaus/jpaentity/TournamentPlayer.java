package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @JoinColumn(nullable = false)
    private TournamentTeam tournamentTeam;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Player player;

    @NotNull
    @Column(nullable = false, columnDefinition = "int default 0")
    private int goals = 0;

    public TournamentPlayer(TournamentTeam tournamentTeam, Player player, int goals) {
        this.tournamentTeam = tournamentTeam;
        this.player = player;
        this.goals = goals;
    }
}
