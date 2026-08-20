package fr.alium.dndapi.feature.creature.entity.dto;

import fr.alium.dndapi.feature.actionDnd.entity.dto.ActionDTO;
import fr.alium.dndapi.feature.creature.entity.Difficulty;
import fr.alium.dndapi.feature.creature.entity.Health;
import fr.alium.dndapi.feature.creature.entity.Speed;
import fr.alium.dndapi.feature.creature.entity.Stat;
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
public class CreatureDTO {
    private String Name;
    private String Description;
    private String Size;
    private String CreatureType;
    private String Alignment;
    private String Habitat;
    private String Treasure;
    private String Book;
    private String Image;
    private Integer BaseCA;
    private String CAspecification;
    private Health Health;
    private List<Speed> Speeds;
    private List<Stat> Stats;
    private List<DefenseDTO> Resistances;
    private List<DefenseDTO> Immunities;
    private List<DefenseDTO> Vulnerabilities;
    private Map<String, Integer> Skills;
    private Map<String, Integer> Senses;
    private List<String> Gears;
    private List<String> Languages;
    private Difficulty Difficulty;
    private List<ActionDTO> Actions;
}
