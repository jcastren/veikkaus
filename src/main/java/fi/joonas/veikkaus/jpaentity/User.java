package fi.joonas.veikkaus.jpaentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String name;

    private String password;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private UserRole userRole;

    public User(String email, String name, String password, UserRole userRole) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
    }
}