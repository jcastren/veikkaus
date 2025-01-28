package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.jpaentity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleGuiEntity {

    private String id;
    private String name;

    public UserRole toDbEntity() {

        return UserRole.builder()
                .id(idValue(id))
                .name(name)
                .build();
    }
}