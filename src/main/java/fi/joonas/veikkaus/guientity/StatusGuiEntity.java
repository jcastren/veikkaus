package fi.joonas.veikkaus.guientity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusGuiEntity {

    private String id;

    @NotNull(message = "{status.statusnumber.notempty}")
    @Max(5)
    private int statusNumber;

    @NotBlank(message = "{status.description.notempty}")
    private String description;
}