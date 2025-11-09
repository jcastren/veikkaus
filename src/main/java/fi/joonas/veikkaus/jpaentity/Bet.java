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
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Tournament tournament;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Status status;

    public Bet(User user, Tournament tournament, Status status) {
        this.user = user;
        this.tournament = tournament;
        this.status = status;
    }
}