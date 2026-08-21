package fr.alium.dndapi.feature.creature.entity;

import fr.alium.dndapi.feature.actionDnd.entity.ActionDnD;
import fr.alium.dndapi.feature.creature.entity.enums.SenseEnum;
import fr.alium.dndapi.feature.creature.entity.enums.SkillEnum;
import fr.alium.dndapi.feature.language.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull
    private String size;
    @NotNull
    private String type;
    @NotNull
    private String alignment;
    private String habitat;
    private String treasure;
    private String book;
    private String image;
    @NotNull
    @Embedded
    private Health health;
    @NotNull
    private Integer baseCA;
    private String CAspecification;
    @NotEmpty
    @ElementCollection
    private List<Speed> speeds;

    @ElementCollection
    private List<Defense> resistances;
    @ElementCollection
    private List<Defense> immunities;
    @ElementCollection
    private List<Defense> vulnerabilities;

    @NotEmpty
    @ElementCollection
    @Size(min = 7, max = 7, message = "il y a 7 stats dans D&D, initiative compris")
    private List<Stat> stats;
    @ElementCollection
    private Map<SkillEnum, Integer> skills;
    @ElementCollection
    private Map<SenseEnum, Integer> senses;
    @ElementCollection
    private List<String> gears;
    @ManyToMany
    private List<Language> languages;
    @Embedded
    private Difficulty difficulty;
    @ManyToMany
    private List<ActionDnD> actions;
}