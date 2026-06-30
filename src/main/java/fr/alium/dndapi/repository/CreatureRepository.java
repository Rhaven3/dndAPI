package fr.alium.dndapi.repository;

import fr.alium.dndapi.entity.Creature;
import fr.alium.dndapi.entity.dto.CreatureXpDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreatureRepository extends JpaRepository<Creature, Long> {
    @Query("SELECT c.id as id, c.CR as CR FROM Creature c")
    List<CreatureXpDTO> findAllAsCreatureXpDTO();
}
