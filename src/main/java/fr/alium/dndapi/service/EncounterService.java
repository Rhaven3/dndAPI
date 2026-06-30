package fr.alium.dndapi.service;

import fr.alium.dndapi.entity.Creature;
import fr.alium.dndapi.entity.Encounter;
import fr.alium.dndapi.entity.enums.EncounterDifficultyEnum;
import fr.alium.dndapi.repository.EncounterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EncounterService {
    EncounterRepository encounterRepository;
    CreatureService creatureService;

    public EncounterService(EncounterRepository encounterRepository,  CreatureService creatureService) {
        this.encounterRepository = encounterRepository;
        this.creatureService = creatureService;
    }

    public ResponseEntity<?> generate(EncounterDifficultyEnum difficulty, Integer challengeRate, Integer partySize, Integer partyAverageLvl, Integer numberEncounters, Integer numberCreatures) {
        if (difficulty == null) return ResponseEntity.badRequest().build();
        if (partyAverageLvl == null) return ResponseEntity.badRequest().build();
//        if (challengeRate == null) return ResponseEntity.badRequest().build();
        if (partySize == null) partySize = 4;
        if (numberEncounters == null) numberEncounters = 1;
        if (numberCreatures == null) return ResponseEntity.badRequest().build();

        int Xptreshold = getXpTreshold(difficulty, partyAverageLvl) * partySize;
        int xpdaily = getXpDaily(partyAverageLvl) * partySize;

        List<Creature> creatures = creatureService.findByXpTreshold(Xptreshold, numberCreatures);


        Encounter encounter = Encounter.builder()
                .name("Generated Encounter " + Xptreshold)
                .creatures()
                .build();
        encounterRepository.save(encounter);

        return ResponseEntity.ok(encounter);
    }

    private int getXpDaily(Integer partyAverageLvl) {
        int[] xpDailyTable = {
                300, 600, 1200, 1700, 3500,
                4000, 5000, 6000, 7500, 9000,
                10500, 11500, 13500, 15000, 18000,
                20000, 25000, 27000, 30000, 40000
        };

        return xpDailyTable[partyAverageLvl];
    }

    private int getXpTreshold(EncounterDifficultyEnum difficulty, Integer partyAverageLvl) {
        List<List<Integer>> xpTresholdTable = new ArrayList<>();
        xpTresholdTable.add(List.of(25, 50, 75, 100));
        xpTresholdTable.add(List.of(50, 100, 150, 200));
        xpTresholdTable.add(List.of(75, 150, 225, 400));
        xpTresholdTable.add(List.of(125, 250, 375, 500));
        xpTresholdTable.add(List.of(250, 500, 750, 1100));
        xpTresholdTable.add(List.of(300, 600, 900, 1400));
        xpTresholdTable.add(List.of(350, 750, 1100, 1700));
        xpTresholdTable.add(List.of(450, 900, 1400, 2100));
        xpTresholdTable.add(List.of(550, 1100, 1600, 2400));
        xpTresholdTable.add(List.of(600, 1200, 1900, 2800));
        xpTresholdTable.add(List.of(800, 1600, 2400, 3600));
        xpTresholdTable.add(List.of(1000, 2000, 3000, 4500));
        xpTresholdTable.add(List.of(1100, 2200, 3400, 5100));
        xpTresholdTable.add(List.of(1250, 2500, 3800, 5700));
        xpTresholdTable.add(List.of(1400, 2800, 4300, 6400));
        xpTresholdTable.add(List.of(1600, 3200, 4800, 7200));
        xpTresholdTable.add(List.of(2000, 3900, 5900, 8800));
        xpTresholdTable.add(List.of(2100, 4200, 6300, 9500));
        xpTresholdTable.add(List.of(2400, 4900, 7300, 10900));
        xpTresholdTable.add(List.of(2800, 5700, 8500, 12700));

        return xpTresholdTable.get(partyAverageLvl).get(difficulty.getValue());
    }
}
