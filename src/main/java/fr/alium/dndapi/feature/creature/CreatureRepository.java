package fr.alium.dndapi.feature.creature;

import fr.alium.dndapi.feature.creature.entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreatureRepository extends JpaRepository<Creature, Long> {
    @Query("SELECT c FROM Creature c WHERE c.difficulty.ChallengeRate = :challengeRate")
    List<Creature> findAllByChallengeRate(@Param("challengeRate") float challengeRate);
}
