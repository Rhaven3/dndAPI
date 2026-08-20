package fr.alium.dndapi.feature.creature.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Stat {
    @NotNull
    private String name;
    @NotNull
    private Integer value;
    @NotNull
    private Integer modifier;
    @NotNull
    private Integer save;
}
