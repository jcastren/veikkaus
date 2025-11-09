package fi.joonas.veikkaus.jpaentity;

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
public class TournamentTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Tournament tournament;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Team team;

    public TournamentTeam(Tournament tournament, Team team) {
        this.tournament = tournament;
        this.team = team;
    }
}