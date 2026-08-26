package fr.alium.dndapi.feature.creature;

import fr.alium.dndapi.feature.actionDnd.ActionDnDRepository;
import fr.alium.dndapi.feature.actionDnd.ActionMapper;
import fr.alium.dndapi.feature.actionDnd.entity.ActionDnD;
import fr.alium.dndapi.feature.actionDnd.entity.dto.ActionDTO;
import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.creature.entity.Difficulty;
import fr.alium.dndapi.feature.creature.entity.dto.CreatureDTO;
import fr.alium.dndapi.feature.creature.entity.dto.CreatureResponseDTO;
import fr.alium.dndapi.feature.language.Language;
import fr.alium.dndapi.feature.language.LanguageRepository;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreatureService {
    private final CreatureRepository creatureRepository;
    private final LanguageRepository languageRepository;
    private final ActionDnDRepository actionDnDRepository;
    private final ActionMapper actionMapper;
    private final CreatureMapper creatureMapper;

    private final List<List<Integer>> subsetXP;
    private final List<Integer> xpTable;

    public CreatureService(CreatureRepository creatureRepository, LanguageRepository languageRepository, ActionDnDRepository actionDnDRepository, ActionMapper actionMapper, CreatureMapper creatureMapper) {
        this.creatureRepository = creatureRepository;
        this.languageRepository = languageRepository;
        this.actionDnDRepository = actionDnDRepository;
        this.actionMapper = actionMapper;
        this.creatureMapper = creatureMapper;
        this.subsetXP = new ArrayList<>();
        this.xpTable = Arrays.asList(25, 50, 100,
                200, 450, 700, 1100, 1800, 2300, 2900, 3900, 5000, 5900,
                7200, 8400, 10000, 11500, 13000, 15000, 18000, 20000, 22000, 25000,
                33000, 41000, 5000, 62000, 75000, 90000, 105000, 120000, 135000, 155000
        );
    }

    public List<Creature> findByXpTreshold(int xptreshold, int numberCreatures) {
        Random random = new Random();
        List<Creature> creatures = new ArrayList<>();
        List<List<Integer>> allSubsetCR = findAllSubsetCR(xptreshold, numberCreatures);
        List<Integer> subsetCR = allSubsetCR.get(random.nextInt(allSubsetCR.size()));

        for (Integer cr : subsetCR) {
            creatures.add(findRandomByCR(cr));
        }
        return creatures;
    }

    public List<List<Integer>> findAllSubsetCR(int sumTarget, int numberCreatures) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(numberCreatures, sumTarget, 0, new Stack<>(), 0, 0);

        for (List<Integer> listofXP : subsetXP) {
            List<Integer> newList = new ArrayList<>();

            for (Integer XP : listofXP) {
                newList.add(xpTable.indexOf(XP) + 1);
            }
            result.add(newList);
        }

        subsetXP.clear();
        return result;
    }

    private void backtrack(int numberCR, int targetSum, int start, Stack<Integer> current, int currentSum, int countCR) {
        if (countCR == numberCR && currentSum == targetSum) {
            subsetXP.add(current);
            return;
        }
        if (countCR >= numberCR || currentSum >= targetSum) {
            return;
        }
        for (int i = start; i < xpTable.size(); i++) {
            current.push(xpTable.get(i));
            backtrack(numberCR, targetSum, i + 1, current, currentSum + xpTable.get(i), countCR + 1);
            current.pop();
        }
    }

    public Creature findRandomByCR(float cr) {
        List<Creature> creatures = creatureRepository.findAllByDifficulty(Difficulty.builder().ChallengeRate(cr).build());
        Random random = new Random();
        int index = random.nextInt(creatures.size());
        return creatures.get(index);
    }

    public int getXp(Creature creature) {
        float cr = creature.getDifficulty().getChallengeRate();
        return xpTable.get((int) Math.ceil(cr) - 1);
    }

    public void create(CreatureDTO creatureDTO) {

        // creation language enfant
        List<Language> languages = new ArrayList<>();
        if (!creatureDTO.getLanguages().isEmpty()) {
            for (String language : creatureDTO.getLanguages()) {
                Language languageEntity = Language.builder()
                        .name(language)
                        .build();
                Language languageExistant = languageRepository.findByName(language);
                if (languageExistant == null) {
                    languageRepository.save(languageEntity);
                    languages.add(languageEntity);
                } else {
                    languages.add(languageExistant);
                }
            }
        }

        List<ActionDnD> actions = new ArrayList<>();

        Creature creature = creatureMapper.toEntity(creatureDTO);
        creature.setActions(actions);
        creature.setLanguages(languages);

        creatureRepository.save(creature);

        // creation ActionDnD enfants
        if (!creatureDTO.getActions().isEmpty()) {
            for (ActionDTO action : creatureDTO.getActions()) {
                ActionDnD actionDnD = actionMapper.toEntity(action);
                actionDnD.setCreature(creature);
                actions.add(actionDnD);
            }
        }

        creature.setActions(actions);
        creatureRepository.save(creature);
    }

    @Transactional(readOnly = true) // Garde la session ouverte
    public List<CreatureResponseDTO> getAllCreatures() {
        List<Creature> creatures = creatureRepository.findAll();

        // Force l'initialisation des collections LAZY
        creatures.forEach(this::initialiseCollections);

        return creatures.stream()
                .map(creatureMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Creature findById(Long id) {
        Creature creature = creatureRepository.findById(id).orElse(null);
        initialiseCollections(creature);
        return creature;
    }

    private void initialiseCollections(Creature creature) {
        if (creature == null) {
            return;
        }
        Hibernate.initialize(creature.getSpeeds());
        Hibernate.initialize(creature.getSkills());
        Hibernate.initialize(creature.getSenses());
        Hibernate.initialize(creature.getGears());
        Hibernate.initialize(creature.getActions());
        Hibernate.initialize(creature.getResistances());
        Hibernate.initialize(creature.getImmunities());
        Hibernate.initialize(creature.getVulnerabilities());
        Hibernate.initialize(creature.getStats());
        Hibernate.initialize(creature.getLanguages());
    }

    @Transactional
    public boolean update(@Valid CreatureResponseDTO creatureResponseDto) {
        boolean exists = creatureRepository.existsById(creatureResponseDto.getId());
        if (!exists) {
            return false;
        }

        Creature creature = creatureMapper.toEntityFromResponseDto(creatureResponseDto);

        // creation language enfant
        if (!creatureResponseDto.getLanguages().isEmpty()) {
            languageRepository.saveAll(creatureResponseDto.getLanguages());
        }

        List<ActionDnD> actions = new ArrayList<>();
        if (!creatureResponseDto.getActions().isEmpty()) {
            actions = creatureResponseDto.getActions().stream()
                    .map(action -> {
                        ActionDnD actionDnD = actionMapper.toEntityFromResponseDto(action);
                        actionDnD.setCreature(creature);
                        return actionDnD;
                    })
                    .toList();
        }

        actionDnDRepository.saveAll(actions);
        creatureRepository.save(creature);
        return true;
    }
}