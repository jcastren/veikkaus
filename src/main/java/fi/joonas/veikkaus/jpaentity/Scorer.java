package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @JoinColumn(nullable = false)
    private TournamentPlayer tournamentPlayer;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Game game;

    public Scorer(TournamentPlayer tournamentPlayer, Game game) {

        this.tournamentPlayer = tournamentPlayer;
        this.game = game;
    }
}