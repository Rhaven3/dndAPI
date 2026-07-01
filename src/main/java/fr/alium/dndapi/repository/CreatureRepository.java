package fr.alium.dndapi.repository;

import fr.alium.dndapi.entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreatureRepository extends JpaRepository<Creature, Long> {
    List<Creature> findAllByCr(int cr);
}
