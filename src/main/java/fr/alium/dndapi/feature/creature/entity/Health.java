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
public class Health {
    @NotNull
    private Integer MaxHealth;
    private Integer MaxHitDice;
    private Integer HitDice;
    private Integer Bonus;
    private String Specification;
}
