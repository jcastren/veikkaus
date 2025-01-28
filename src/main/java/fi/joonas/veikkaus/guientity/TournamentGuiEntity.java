package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.jpaentity.Tournament;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;
import static java.lang.Integer.parseInt;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentGuiEntity {

    private String id;
    private String name;
    private String year;

    public Tournament toDbEntity() {

        return Tournament.builder()
                .id(idValue(id))
                .name(name)
                .year(parseInt(year))
                .build();
    }
}