package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
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

    public TournamentTeamGuiEntity toGuiEntity() {

        return TournamentTeamGuiEntity.builder()
                .id(valueOf(id))
                .tournament(tournament.toGuiEntity())
                .team(team.toGuiEntity())
                .build();
    }


}