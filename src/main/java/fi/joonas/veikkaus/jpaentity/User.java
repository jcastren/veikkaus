package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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
}