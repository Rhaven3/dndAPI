package fr.alium.dndapi.feature.encounter;

import fr.alium.dndapi.common.EntityMapper;
import fr.alium.dndapi.feature.creature.CreatureMapper;
import fr.alium.dndapi.feature.creature.entity.dto.CreatureResponseDTO;
import fr.alium.dndapi.feature.encounter.entity.Encounter;
import fr.alium.dndapi.feature.encounter.entity.dto.EncounterDTO;
import fr.alium.dndapi.feature.encounter.entity.dto.EncounterResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EncounterMapper implements EntityMapper<Encounter, EncounterDTO> {
    private final CreatureMapper creatureMapper;

    public EncounterMapper(CreatureMapper creatureMapper) {
        this.creatureMapper = creatureMapper;
    }

    @Override
    public EncounterDTO toDto(Encounter encounter) {
        return null;
    }

    public EncounterResponseDTO toResponseDto(Encounter encounter) {
        List<CreatureResponseDTO> creatures = new ArrayList<>();
        if(encounter.getCreatures() != null) {
            creatures = encounter.getCreatures().stream()
                    .map(creatureMapper::toResponseDto)
                    .toList();
        }

        return EncounterResponseDTO.builder()
                .id(encounter.getId())
                .name(encounter.getName())
                .description(encounter.getDescription())
                .creatures(creatures)
                .build();
    }

    @Override
    public Encounter toEntity(EncounterDTO encounterDTO) {
        return null;
    }


}
