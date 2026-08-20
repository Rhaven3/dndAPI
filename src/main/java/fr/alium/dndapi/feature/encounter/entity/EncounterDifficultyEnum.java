package fr.alium.dndapi.feature.encounter.entity;

import lombok.Getter;

@Getter
public enum EncounterDifficultyEnum {
    EASY(0),
    MEDIUM(1),
    HARD(2),
    MORTAL(3);

    private final int value;
    EncounterDifficultyEnum(int difficulty) {
        this.value = difficulty;
    }
}
