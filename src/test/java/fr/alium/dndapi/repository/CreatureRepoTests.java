package fr.alium.dndapi.repository;

import fr.alium.dndapi.entity.Creature;
import fr.alium.dndapi.entity.Language;
import fr.alium.dndapi.entity.Trait;
import fr.alium.dndapi.entity.enums.SenseEnum;
import fr.alium.dndapi.entity.enums.StatEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CreatureRepoTests {
    @Autowired
    private CreatureRepository creatureRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private TraitRepository traitRepository;

    private Long sizeCreature;
    private List<Language> languages;
    private Trait trait;
    private Creature creature;

    @BeforeEach
    public void setup() {
        trait = Trait.builder().name("Marche sur la glace")
                .description("Le dragon peut parcourir et gravir toute surface gelée sans passer par le moindre jet de caractéristique. En outre, tout Terrain difficile composé de glace ou de neige ne lui demande aucune dépense de déplacement supplémentaire.")
                .build();

        Language lang1 = Language.builder().name("commun").build();
        Language lang2 = Language.builder().name("draconique").build();
        languages = Arrays.asList(lang1, lang2);

        Map<StatEnum, Integer> mapStats = new HashMap<>();
        mapStats.put(StatEnum.STRENGTH, 18);
        mapStats.put(StatEnum.DEXTERITY, 10);
        mapStats.put(StatEnum.CONSTITUTION, 18);
        mapStats.put(StatEnum.INTELLIGENCE, 6);
        mapStats.put(StatEnum.WISDOM, 0);
        mapStats.put(StatEnum.CHARISMA, 12);
        Map<SenseEnum, Integer> mapSenses = new HashMap<>();
        mapSenses.put(SenseEnum.BLINDSIGHT, 9);
        mapSenses.put(SenseEnum.PASSIVE_PERCEPTION, 16);
        mapSenses.put(SenseEnum.DARKVISION, 36);

        creature = Creature.builder()
                .name("Dragon blanc, jeune")
                .maxHP(123)
                .maxHD("13d10+52")
                .baseCA(17)
                .initiative(3)
                .stats(mapStats)
                .senses(mapSenses)
                .languages(languages)
                .CR(6)
                .traits(List.of(trait))
                .build();

        sizeCreature = creatureRepository.count();

        languageRepository.saveAll(languages);
        traitRepository.save(trait);
        creatureRepository.save(creature);
    }

//    @AfterEach
//    public void tearDown() {
//        creatureRepository.delete(creature);
//        languageRepository.deleteAll(languages);
//        traitRepository.delete(trait);
//    }

    @Test
    public void create() {
        Assertions.assertEquals(sizeCreature+1, creatureRepository.count());
    }
}
