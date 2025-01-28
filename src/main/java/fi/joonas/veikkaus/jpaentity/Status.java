package fi.joonas.veikkaus.jpaentity;

import fi.joonas.veikkaus.guientity.StatusGuiEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int statusNumber;

    private String description;

    public Status(int statusNumber, String description) {

        this.statusNumber = statusNumber;
        this.description = description;
    }

    public StatusGuiEntity toGuiEntity() {

        return StatusGuiEntity.builder()
                .id(valueOf(id))
                .statusNumber(statusNumber)
                .description(description)
                .build();
    }
}