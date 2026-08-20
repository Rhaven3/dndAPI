package fr.alium.dndapi.feature.encounter;

import fr.alium.dndapi.feature.encounter.entity.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {
}
