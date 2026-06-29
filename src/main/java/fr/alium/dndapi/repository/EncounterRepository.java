package fr.alium.dndapi.repository;

import fr.alium.dndapi.entity.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {
}
