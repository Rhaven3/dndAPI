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
public class Speed {
    @NotNull
    private String Name;
    @NotNull
    private Integer Value;
    private String Specification;
}
