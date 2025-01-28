package fi.joonas.veikkaus.guientity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerGuiEntity {

    private String id;
    private String firstName;
    private String lastName;
}