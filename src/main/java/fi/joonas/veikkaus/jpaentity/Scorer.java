package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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
}