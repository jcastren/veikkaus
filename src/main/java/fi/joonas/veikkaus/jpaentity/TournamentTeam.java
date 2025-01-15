package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.*;
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
    private Tournament tournament;

    @ManyToOne
    private Team team;

    public TournamentTeam(Tournament tournament, Team team) {

        this.tournament = tournament;
        this.team = team;
    }
}