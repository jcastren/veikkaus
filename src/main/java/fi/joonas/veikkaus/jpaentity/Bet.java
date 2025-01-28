package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.BetGuiEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.String.valueOf;

@Entity
@Data
@NoArgsConstructor
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Tournament tournament;

    @NotNull
    @ManyToOne
    private Status status;

    public Bet(User user, Tournament tournament, Status status) {

        this.user = user;
        this.tournament = tournament;
        this.status = status;
    }

    public BetGuiEntity toGuiEntity() {

        return BetGuiEntity.builder()
                .id(valueOf(id))
                .user(user.toGuiEntity())
                .tournament(tournament.toGuiEntity())
                .status(status.toGuiEntity())
                .build();
    }
}