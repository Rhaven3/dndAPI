package fr.alium.dndapi.feature.actionDnd.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionDTO {
    private String Name;
    private String Description;
    private String ActionType;
}
