package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.jpaentity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGuiEntity {

    private String id;
    private String email;
    private String name;
    private String password;
    private UserRoleGuiEntity userRole;

    public User toDbEntity() {

        return User.builder()
                .id(idValue(id))
                .email(email)
                .name(name)
                .password(password)
                .userRole(userRole.toDbEntity())
                .build();
    }
}