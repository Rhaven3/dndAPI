package fr.alium.dndapi.feature.encounter;

import fr.alium.dndapi.feature.creature.CreatureService;
import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.encounter.entity.Encounter;
import fr.alium.dndapi.feature.encounter.entity.EncounterDifficultyEnum;
import fr.alium.dndapi.feature.encounter.entity.dto.EncounterResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EncounterService {
    private final EncounterRepository encounterRepository;
    private final CreatureService creatureService;
    private final EncounterMapper encounterMapper;

    public EncounterService(EncounterRepository encounterRepository, CreatureService creatureService, EncounterMapper encounterMapper) {
        this.encounterRepository = encounterRepository;
        this.creatureService = creatureService;
        this.encounterMapper = encounterMapper;
    }

    @Transactional()
    public EncounterResponseDTO generate(EncounterDifficultyEnum difficulty, Integer partySize, Integer partyAverageLvl, Integer numberCreatures) {
        if (partySize == null) partySize = 4;
//        if (numberEncounters == null) numberEncounters = 1;

        int Xptreshold = getXpTreshold(difficulty, partyAverageLvl) * partySize;

        List<Creature> creatures = creatureService.findByXpTreshold(Xptreshold, numberCreatures, 0.15f);

        Encounter encounter = Encounter.builder()
                .name("Generated Encounter " + Xptreshold)
                .creatures(creatures)
                .build();
        encounterRepository.save(encounter);
        for (Creature creature : creatures) {
            creatureService.initialiseCollections(creature);
        }
        EncounterResponseDTO encounterResponseDTO = encounterMapper.toResponseDto(encounter);
        return encounterResponseDTO;
    }

    public int getXpTreshold(EncounterDifficultyEnum difficulty, Integer partyAverageLvl) {
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
