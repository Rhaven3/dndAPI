package fr.alium.dndapi.repository;

import fr.alium.dndapi.entity.Trait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TraitRepository extends JpaRepository<Trait, Long> {
}
