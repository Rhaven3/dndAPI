package fr.alium.dndapi.feature.actionDnd.entity;

import fr.alium.dndapi.feature.creature.entity.Creature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ActionDnD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private ActionEnum type;
    @ManyToOne(fetch = FetchType.LAZY)
    private Creature creature;
}
