package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.TeamGuiEntity;
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
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Team(String name) {

        this.name = name;
    }

    public TeamGuiEntity toGuiEntity() {

        return TeamGuiEntity.builder()
                .id(valueOf(id))
                .name(name)
                .build();
    }
}