package fr.alium.dndapi.feature.creature;

import fr.alium.dndapi.feature.actionDnd.ActionMapper;
import fr.alium.dndapi.feature.actionDnd.entity.ActionDnD;
import fr.alium.dndapi.feature.actionDnd.entity.dto.ActionDTO;
import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.creature.entity.Difficulty;
import fr.alium.dndapi.feature.creature.entity.dto.CreatureDTO;
import fr.alium.dndapi.feature.language.Language;
import fr.alium.dndapi.feature.language.LanguageRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreatureService {
    private final CreatureRepository creatureRepository;
    private final LanguageRepository languageRepository;
    private final ActionMapper actionMapper;
    private final CreatureMapper creatureMapper;

    private final List<List<Integer>> subsetXP;
    private final List<Integer> xpTable;

    public CreatureService(CreatureRepository creatureRepository, LanguageRepository languageRepository, ActionMapper actionMapper, CreatureMapper creatureMapper) {
        this.creatureRepository = creatureRepository;
        this.languageRepository = languageRepository;
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

    public List<Creature> getAllCreatures() {
        List<Creature> creatures = creatureRepository.findAll();
        creatures.forEach(creature -> {
            Hibernate.initialize(creature.getActions());
            Hibernate.initialize(creature.getSkills());
            Hibernate.initialize(creature.getSenses());
            Hibernate.initialize(creature.getLanguages());
            Hibernate.initialize(creature.getStats());
            Hibernate.initialize(creature.getResistances());
            Hibernate.initialize(creature.getImmunities());
            Hibernate.initialize(creature.getVulnerabilities());
//            Hibernate.initialize(creature.getGears());
        });

        return creatures;
    }
}