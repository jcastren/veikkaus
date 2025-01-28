package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.String.valueOf;

@Entity
@Data
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    public UserRole(String name) {

        this.name = name;
    }

    public UserRoleGuiEntity toGuiEntity() {

        return UserRoleGuiEntity.builder()
                .id(valueOf(id))
                .name(name)
                .build();
    }
}