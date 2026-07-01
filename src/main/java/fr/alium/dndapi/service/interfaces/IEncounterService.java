package fr.alium.dndapi.service.interfaces;

import fr.alium.dndapi.entity.Encounter;
import fr.alium.dndapi.entity.enums.EncounterDifficultyEnum;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface IEncounterService {
    Encounter generate(EncounterDifficultyEnum difficulty, Integer partySize, Integer partyAverageLvl, Integer numberCreatures);

    int getXpTreshold(EncounterDifficultyEnum difficulty, Integer partyAverageLvl);

}
