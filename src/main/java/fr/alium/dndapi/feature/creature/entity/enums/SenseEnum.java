package fr.alium.dndapi.feature.creature.entity.enums;

import lombok.Getter;

@Getter
public enum SenseEnum {
    PASSIVE_PERCEPTION("Passive Perception"),
    BLINDSIGHT("Blindsight"),
    TRUESIGHT("Truesight"),
    DARKVISION("Darkvision"),
    TREMORSENSE("Tremorsense");

    private final String text;
    SenseEnum(String text) {
        this.text = text;
    }

    public static SenseEnum fromString(String text) {
        for (SenseEnum senseEnum : values()) {
            if (senseEnum.text.equalsIgnoreCase(text)) {
                return senseEnum;
            }
        }
        return null;
    }
}
