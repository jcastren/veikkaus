package fi.joonas.veikkaus.guientity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusGuiEntity {

    @NotNull
    private Long id;

    @NotNull(message = "{status.statusnumber.notempty}")
    @Max(5)
    private int statusNumber;

    @NotBlank(message = "{status.description.notempty}")
    private String description;
}