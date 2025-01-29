package fi.joonas.veikkaus.guientity;

import fi.joonas.veikkaus.jpaentity.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fi.joonas.veikkaus.util.VeikkausUtil.idValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusGuiEntity {

    private String id;

    @NotNull(message = "{status.statusnumber.notempty}")
    @Max(5)
    private int statusNumber;

    @NotBlank(message = "{status.description.notempty}")
    private String description;

    public Status toDbEntity() {

        return Status.builder()
                .id(idValue(id))
                .statusNumber(statusNumber)
                .description(description)
                .build();
    }
}