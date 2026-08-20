package fr.alium.dndapi.feature.encounter;

import fr.alium.dndapi.feature.encounter.entity.Encounter;
import fr.alium.dndapi.feature.encounter.entity.EncounterDifficultyEnum;

public interface IEncounterService {
    Encounter generate(EncounterDifficultyEnum difficulty, Integer partySize, Integer partyAverageLvl, Integer numberCreatures);

    int getXpTreshold(EncounterDifficultyEnum difficulty, Integer partyAverageLvl);

}
