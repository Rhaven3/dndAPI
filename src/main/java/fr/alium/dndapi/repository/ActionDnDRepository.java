package fr.alium.dndapi.repository;

import fr.alium.dndapi.entity.ActionDnD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ActionDnDRepository extends JpaRepository<ActionDnD, Integer> {
}
