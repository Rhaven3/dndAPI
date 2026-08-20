package fr.alium.dndapi.feature.creature;

import fr.alium.dndapi.feature.creature.entity.Defense;
import fr.alium.dndapi.feature.creature.entity.dto.DefenseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefenseMapper {
    public DefenseDTO toDto(Defense defense) {
        return null;
    }

    public List<Defense> toEntities(DefenseDTO defenseDTO) {
        List<Defense> defenses = new ArrayList<>();

        for (String name : defenseDTO.getNames()) {
            defenses.add(
                    Defense.builder()
                            .name(name)
                            .conditions(defenseDTO.getCondition())
                            .build()
            );
        }
        return defenses;
    }
}
