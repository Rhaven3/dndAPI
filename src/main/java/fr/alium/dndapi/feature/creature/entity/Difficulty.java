package fr.alium.dndapi.feature.creature.entity;

import jakarta.persistence.Column;
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
    private Integer XP;
    private Integer ProficiencyBonus;
    @Size(max = 33)
    @Column(name = "challenge_rate")
    private Float ChallengeRate;
}
