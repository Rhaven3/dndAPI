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

    public static EncounterDifficultyEnum fromInt(int difficulty) {
        return switch (difficulty) {
            case 0 -> EASY;
            case 1 -> MEDIUM;
            case 2 -> HARD;
            case 3 -> MORTAL;
            default -> throw new IllegalArgumentException("Invalid difficulty value: " + difficulty);
        };
    }
}
