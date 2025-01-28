package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.PlayerGuiEntity;
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
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    public Player(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PlayerGuiEntity toGuiEntity() {

        return PlayerGuiEntity.builder()
                .id(valueOf(id))
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}