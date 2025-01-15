package fi.joonas.veikkaus.guientity;

import lombok.Data;

@Data
public class UserGuiEntity {

    private String id;
    private String email;
    private String name;
    private String password;
    private UserRoleGuiEntity userRole;
}