package fr.alium.dndapi.feature.encounter.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncounterDTO {
    private String name;
    private String description;
    private List<Integer> creatures;
}
