package fr.alium.dndapi.feature.actionDnd.entity;

import lombok.Getter;

@Getter
public enum ActionEnum {
    ACTION("actions"),
    BONUS_ACTION("bonus actions"),
    REACTION("reactions"),
    LEGENDARY_ACTION("legendary actions"),
    TRAIT("traits");

    private final String text;
    ActionEnum(String text) {
        this.text = text;
    }

    public static ActionEnum fromString(String text) {
        for (ActionEnum actionEnum : ActionEnum.values()) {
            if (actionEnum.text.equalsIgnoreCase(text)) {
                return actionEnum;
            }
        }
        return null;
    }
}
