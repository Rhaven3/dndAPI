package fr.alium.dndapi.feature.actionDnd;

import fr.alium.dndapi.feature.actionDnd.entity.ActionDnD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionDnDRepository extends JpaRepository<ActionDnD, Integer> {
}
