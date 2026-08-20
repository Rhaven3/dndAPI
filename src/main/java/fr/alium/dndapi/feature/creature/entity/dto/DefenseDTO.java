package fr.alium.dndapi.feature.creature.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefenseDTO {
    private List<String> Names;
    private String Condition;
}
