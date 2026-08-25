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
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@BatchSize(size = 3)
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
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Speed> speeds;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Defense> resistances;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Defense> immunities;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Defense> vulnerabilities;

    @NotEmpty
    @ElementCollection(fetch = FetchType.LAZY)
    @Size(min = 7, max = 7, message = "il y a 7 stats dans D&D, initiative compris")
    private List<Stat> stats;
    @ElementCollection(fetch = FetchType.LAZY)
    private Map<SkillEnum, Integer> skills;
    @ElementCollection(fetch = FetchType.LAZY)
    private Map<SenseEnum, Integer> senses;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> gears;
    @ManyToMany
    private List<Language> languages;
    @Embedded
    private Difficulty difficulty;
    @OneToMany(mappedBy = "creature", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActionDnD> actions;
}