package fr.alium.dndapi.feature.creature;

import fr.alium.dndapi.common.EntityMapper;
import fr.alium.dndapi.feature.actionDnd.ActionMapper;
import fr.alium.dndapi.feature.actionDnd.entity.ActionDnD;
import fr.alium.dndapi.feature.actionDnd.entity.dto.ActionDTO;
import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.creature.entity.Defense;
import fr.alium.dndapi.feature.creature.entity.dto.CreatureDTO;
import fr.alium.dndapi.feature.creature.entity.dto.DefenseDTO;
import fr.alium.dndapi.feature.creature.entity.enums.SenseEnum;
import fr.alium.dndapi.feature.creature.entity.enums.SkillEnum;
import fr.alium.dndapi.feature.language.Language;
import fr.alium.dndapi.feature.language.LanguageRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CreatureMapper implements EntityMapper<Creature, CreatureDTO> {
    private final LanguageRepository languageRepository;
    private final ActionMapper actionMapper;
    private final DefenseMapper defenseMapper;

    public CreatureMapper(LanguageRepository languageRepository, ActionMapper actionMapper, DefenseMapper defenseMapper) {
        this.languageRepository = languageRepository;
        this.actionMapper = actionMapper;
        this.defenseMapper = defenseMapper;
    }

    @Override
    public CreatureDTO toDto(Creature creature) {
        return null;
    }

    @Override
    public Creature toEntity(CreatureDTO creatureDTO) {
        Map<SkillEnum, Integer> skills = new HashMap<>();
        for (Map.Entry<String, Integer> entry : creatureDTO.getSkills().entrySet()) {
            skills.put(SkillEnum.fromString(entry.getKey()), entry.getValue());
        }

        Map<SenseEnum, Integer> senses = new HashMap<>();
        for (Map.Entry<String, Integer> entry : creatureDTO.getSenses().entrySet()) {
            senses.put(SenseEnum.fromString(entry.getKey()), entry.getValue());
        }

        List<Language> languages = new ArrayList<>();
        for (String languageName : creatureDTO.getLanguages()) {
            languages.add(languageRepository.findByName(languageName));
        }
        List<ActionDnD> actions = new ArrayList<>();
        for (ActionDTO action : creatureDTO.getActions()) {
            actions.add(actionMapper.toEntity(action));
        }

        // defenses
        List<Defense> resistances = new ArrayList<>();
        List<Defense> immunities = new ArrayList<>();
        List<Defense> vulnerabilities = new ArrayList<>();

        if (creatureDTO.getResistances() != null) {
            for (DefenseDTO defenseDTO : creatureDTO.getResistances()) {
                resistances.addAll(defenseMapper.toEntities(defenseDTO));
            }
        }
        if (creatureDTO.getImmunities() != null) {
            for (DefenseDTO defenseDTO : creatureDTO.getImmunities()) {
                immunities.addAll(defenseMapper.toEntities(defenseDTO));
            }
        }

        if (creatureDTO.getVulnerabilities() != null) {
            for (DefenseDTO defenseDTO : creatureDTO.getVulnerabilities()) {
                vulnerabilities.addAll(defenseMapper.toEntities(defenseDTO));
            }
        }


        return Creature.builder()
                .name(creatureDTO.getName())
                .description(creatureDTO.getDescription())
                .size(creatureDTO.getSize())
                .type(creatureDTO.getCreatureType())
                .alignment(creatureDTO.getAlignment())
                .habitat(creatureDTO.getHabitat())
                .treasure(creatureDTO.getTreasure())
                .book(creatureDTO.getBook())
                .image(creatureDTO.getImage())
                .health(creatureDTO.getHealth())
                .baseCA(creatureDTO.getBaseCA())
                .CAspecification(creatureDTO.getCAspecification())
                .speeds(creatureDTO.getSpeeds())
                .stats(creatureDTO.getStats())
                .vulnerabilities(vulnerabilities)
                .resistances(resistances)
                .immunities(immunities)
                .skills(skills)
                .senses(senses)
                .languages(languages)
                .gears(creatureDTO.getGears())
                .difficulty(creatureDTO.getDifficulty())
                .actions(actions)
                .build();
    }
}
