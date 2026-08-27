package fr.alium.dndapi.feature.encounter.entity.dto;


import fr.alium.dndapi.feature.creature.entity.dto.CreatureResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncounterResponseDTO {
    private Long id;
    private String name;
    private String description;
    private List<CreatureResponseDTO> creatures;
}
