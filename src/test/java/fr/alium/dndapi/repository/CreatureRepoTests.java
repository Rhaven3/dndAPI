package fr.alium.dndapi.repository;

import fr.alium.dndapi.feature.actionDnd.entity.ActionDnD;
import fr.alium.dndapi.feature.actionDnd.entity.ActionEnum;
import fr.alium.dndapi.feature.creature.CreatureRepository;
import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.creature.entity.Difficulty;
import fr.alium.dndapi.feature.creature.entity.Health;
import fr.alium.dndapi.feature.creature.entity.Stat;
import fr.alium.dndapi.feature.creature.entity.enums.SenseEnum;
import fr.alium.dndapi.feature.creature.entity.enums.StatEnum;
import fr.alium.dndapi.feature.language.Language;
import fr.alium.dndapi.feature.language.LanguageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class CreatureRepoTests {
    @Autowired
    private CreatureRepository creatureRepository;
    @Autowired
    private LanguageRepository languageRepository;

    private Long sizeCreature;
    private List<Language> languages;
    private Creature creature;

    @BeforeEach
    public void setup() {
        ActionDnD action = ActionDnD.builder().name("Marche sur la glace")
                .description("Le dragon peut parcourir et gravir toute surface gelée sans passer par le moindre jet de caractéristique. En outre, tout Terrain difficile composé de glace ou de neige ne lui demande aucune dépense de déplacement supplémentaire.")
                .type(ActionEnum.TRAIT)
                .build();

        Language lang1 = Language.builder().name("commun").build();
        Language lang2 = Language.builder().name("draconique").build();
        languages = Arrays.asList(lang1, lang2);


        List<Stat> stats = new ArrayList<>();
        stats.add(Stat.builder().value(18).name(StatEnum.STRENGTH.toString()).build());
        stats.add(Stat.builder().value(10).name(StatEnum.DEXTERITY.toString()).build());
        stats.add(Stat.builder().value(18).name(StatEnum.CONSTITUTION.toString()).build());
        stats.add(Stat.builder().value(6).name(StatEnum.INTELLIGENCE.toString()).build());
        stats.add(Stat.builder().value(0).name(StatEnum.WISDOM.toString()).build());
        stats.add(Stat.builder().value(12).name(StatEnum.CHARISMA.toString()).build());
        stats.add(Stat.builder().value(3).name(StatEnum.INITIATIVE.toString()).build());

        Map<SenseEnum, Integer> mapSenses = new HashMap<>();
        mapSenses.put(SenseEnum.BLINDSIGHT, 9);
        mapSenses.put(SenseEnum.PASSIVE_PERCEPTION, 16);
        mapSenses.put(SenseEnum.DARKVISION, 36);

        creature = Creature.builder()
                .name("Dragon blanc, jeune")
                .health(Health.builder()
                        .maxHP(123)
                        .maxHD(13)
                        .hitDice(10)
                        .bonus(52)
                        .build())
                .baseCA(17)
                .stats(stats)
                .senses(mapSenses)
                .languages(languages)
                .difficulty(Difficulty.builder()
                        .challengeRating(6)
                        .build())
                .actions(List.of(action))
                .build();

        sizeCreature = creatureRepository.count();

        languageRepository.saveAll(languages);
        creatureRepository.save(creature);
    }

    @AfterEach
    public void tearDown() {
        creatureRepository.delete(creature);
        languageRepository.deleteAll(languages);
    }

    @Test
    public void create() {
        Assertions.assertEquals(sizeCreature + 1, creatureRepository.count());
    }
}
