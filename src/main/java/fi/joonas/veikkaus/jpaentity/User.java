package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.UserGuiEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.String.valueOf;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String name;

    private String password;

    @ManyToOne
    private UserRole userRole;

    public User(String email, String name, String password, UserRole userRole) {

        super();
        this.email = email;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
    }

    public UserGuiEntity toGuiEntity() {

        return UserGuiEntity.builder()
                .id(valueOf(id))
                .email(email)
                .name(name)
                .password(password)
                .userRole(userRole.toGuiEntity())
                .build();
    }
}