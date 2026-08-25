package fr.alium.dndapi.feature.creature;

import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.creature.entity.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreatureRepository extends JpaRepository<Creature, Long> {
    List<Creature> findAllByDifficulty(Difficulty difficulty);
}
