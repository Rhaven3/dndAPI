package fr.alium.dndapi.entity;

import fr.alium.dndapi.entity.enums.SenseEnum;
import fr.alium.dndapi.entity.enums.SkillEnum;
import fr.alium.dndapi.entity.enums.StatEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Creature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer maxHP;
    private String maxHD;
    private Integer baseCA;
    private Integer initiative;
    @ElementCollection
    private Map<StatEnum, Integer> stats;
    private List<SkillEnum>  skills;
    @ElementCollection
    private Map<SenseEnum, Integer> senses;
    @ManyToMany
    private List<Language> languages;
    private Integer CR;
    @ManyToMany
    private List<Trait> traits;
    @ManyToMany
    private List<ActionDnD> actions;
    @ManyToMany
    private List<ActionDnD> bonusActions;
    @ManyToMany
    private List<ActionDnD> reactions;
}