package fr.alium.dndapi.service;

import fr.alium.dndapi.feature.creature.CreatureService;
import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.encounter.EncounterService;
import fr.alium.dndapi.feature.encounter.entity.Encounter;
import fr.alium.dndapi.feature.encounter.entity.EncounterDifficultyEnum;
import fr.alium.dndapi.feature.encounter.EncounterRepository;
import fr.alium.dndapi.feature.encounter.entity.dto.EncounterResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EncounterServiceTests {
    @Autowired
    EncounterService encounterService;
    @Autowired
    EncounterRepository encounterRepository;
    @Autowired
    CreatureService creatureService;

    public EncounterServiceTests(EncounterService encounterService,  EncounterRepository encounterRepository, CreatureService creatureService) {
        this.encounterService = encounterService;
        this.encounterRepository = encounterRepository;
        this.creatureService = creatureService;
    }

    Long encounterSize;
    @BeforeEach
    public void setup() {
        encounterSize = encounterRepository.count();
    }


    @Test
    public void testGenerateEncounter() {
//        EncounterResponseDTO encounterResponseDto = encounterService.generate(
//                EncounterDifficultyEnum.MEDIUM,
//                4,
//                5,
//                4
//        );
//
//        assert encounterSize+1 == encounterRepository.count();
//
//        int xpTotal = 0;
//        for (Creature creature : encounter.getCreatures()) {
//            xpTotal += creatureService.getXp(creature);
//        }
//
//        int xpTreshold =  encounterService.getXpTreshold(EncounterDifficultyEnum.MEDIUM, 5);
//
//        assert xpTotal == xpTreshold;
//
//        encounterRepository.delete(encounter);
    }
}
