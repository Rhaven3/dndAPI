package fr.alium.dndapi.entity;

import fr.alium.dndapi.entity.enums.SenseEnum;
import fr.alium.dndapi.entity.enums.SkillEnum;
import fr.alium.dndapi.entity.enums.StatEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @NotNull
    private Integer maxHP;
    private String maxHD;
    @NotNull
    private Integer baseCA;

    private Integer initiative;
    @ElementCollection
    @NotEmpty
    @Size(min = 6, max = 6, message = "il y a 6 stats dans D&D fdp")
    private Map<StatEnum, Integer> stats;
    private List<SkillEnum>  skills;
    @ElementCollection
    private Map<SenseEnum, Integer> senses;
    @ManyToMany
    private List<Language> languages;
    @Size(min = 0, max = 33)
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