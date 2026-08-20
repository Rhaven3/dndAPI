package fr.alium.dndapi.feature.creature.entity.enums;

import lombok.Getter;

@Getter
public enum SkillEnum {
    ACROBATICS("acrobatics"),
    ANIMAL_HANDLING("animal handling"),
    ARCANA("arcana"),
    ATHLETICS("athletics"),
    DECEPTION("deception"),
    HISTORY("history"),
    INSIGHT("insight"),
    INTIMIDATION("intimidation"),
    INVESTIGATION("investigation"),
    MEDICINE("medicine"),
    NATURE("nature"),
    PERCEPTION("perception"),
    PERSUASION("persuasion"),
    RELIGION("religion"),
    SLEIGHT_OF_HAND("sleight of hand"),
    STEALTH("stealth"),
    SURVIVAL("survival");

    private final String text;
    SkillEnum(String text) {
        this.text = text;
    }

    public static SkillEnum fromString(String text) {
        for (SkillEnum skillEnum : SkillEnum.values()) {
            if (skillEnum.getText().equalsIgnoreCase(text)) {
                return skillEnum;
            }
        }
        return null;
    }
}
