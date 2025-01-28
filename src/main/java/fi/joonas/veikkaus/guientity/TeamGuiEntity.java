package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.jpaentity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamGuiEntity {

    private String id;
    private String name;

    public Team toDbEntity() {

        return Team.builder()
                .id(idValue(id))
                .name(name)
                .build();
    }

}