package fi.joonas.veikkaus.guientity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}