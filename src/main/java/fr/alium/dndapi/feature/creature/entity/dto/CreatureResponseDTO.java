package fr.alium.dndapi.feature.creature.entity.dto;

import fr.alium.dndapi.feature.actionDnd.entity.dto.ActionResponseDTO;
import fr.alium.dndapi.feature.creature.entity.*;
import fr.alium.dndapi.feature.language.Language;
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
public class CreatureResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String size;
    private String type;
    private String alignment;
    private String habitat;
    private String treasure;
    private String book;
    private String image;
    private Health health;
    private Integer baseCA;
    private String caSpecification;
    private List<Speed> speeds;
    private List<Defense> resistances;
    private List<Defense> immunities;
    private List<Defense> vulnerabilities;
    private List<Stat> stats;
    private Map<String, Integer> skills;
    private Map<String, Integer> senses;
    private List<String> gears;
    private List<Language> languages;
    private Difficulty difficulty;
    private List<ActionResponseDTO> actions;
}
