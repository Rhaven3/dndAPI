package fr.alium.dndapi.feature.encounter.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateEncounterDTO {
    private Integer difficulty;
    private Integer partySize;
    private Integer partyAverageLvl;
    private Integer numberCreatures;
}
