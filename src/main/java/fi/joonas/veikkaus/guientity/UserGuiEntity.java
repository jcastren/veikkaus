package fi.joonas.veikkaus.guientity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGuiEntity {

    @NotNull
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String name;

    private String password;

    @NotNull
    private UserRoleGuiEntity userRole;
}