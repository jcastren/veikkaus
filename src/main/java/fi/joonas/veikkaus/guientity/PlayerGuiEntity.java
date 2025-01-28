package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.jpaentity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerGuiEntity {

    private String id;
    private String firstName;
    private String lastName;

    public Player toDbEntity() {

        return Player.builder()
                .id(idValue(id))
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}