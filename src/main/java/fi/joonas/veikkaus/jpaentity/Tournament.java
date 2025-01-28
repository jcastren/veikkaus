package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.String.valueOf;

@Entity
@Data
@NoArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int year;

    public Tournament(String name, int year) {

        this.name = name;
        this.year = year;
    }

    public TournamentGuiEntity toGuiEntity() {

        return TournamentGuiEntity.builder()
                .id(valueOf(id))
                .name(name)
                .year(valueOf(year))

                .build();
    }
}