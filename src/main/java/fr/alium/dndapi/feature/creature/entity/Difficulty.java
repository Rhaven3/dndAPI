package fr.alium.dndapi.feature.creature.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Difficulty {
    private int xp;
    private int proficiencyBonus;
    @Size(max = 33)
    private float challengeRating;
}
