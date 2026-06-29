package fr.alium.dndapi.repository;

import fr.alium.dndapi.entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatureRepository extends JpaRepository<Creature, Long> {
}
