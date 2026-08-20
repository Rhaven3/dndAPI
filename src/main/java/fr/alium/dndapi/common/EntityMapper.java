package fr.alium.dndapi.common;

public interface EntityMapper<Entity, DTO> {
    DTO toDto(Entity entity);
    Entity toEntity(DTO dto);
}
