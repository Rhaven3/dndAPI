package fr.alium.dndapi.feature.actionDnd;

import fr.alium.dndapi.common.EntityMapper;
import fr.alium.dndapi.feature.actionDnd.entity.ActionDnD;
import fr.alium.dndapi.feature.actionDnd.entity.ActionEnum;
import fr.alium.dndapi.feature.actionDnd.entity.dto.ActionDTO;
import org.springframework.stereotype.Component;

@Component
public class ActionMapper implements EntityMapper<ActionDnD, ActionDTO> {
    @Override
    public ActionDTO toDto(ActionDnD actionDnD) {
        return ActionDTO.builder()
                .Name(actionDnD.getName())
                .Description(actionDnD.getDescription())
                .ActionType(actionDnD.getType().getText())
                .build();
    }

    @Override
    public ActionDnD toEntity(ActionDTO actionDTO) {
        return ActionDnD.builder()
                .name(actionDTO.getName())
                .description(actionDTO.getDescription())
                .type(ActionEnum.fromString(actionDTO.getActionType()))
                .build();
    }
}
