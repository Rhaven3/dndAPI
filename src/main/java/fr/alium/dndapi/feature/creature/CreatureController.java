package fr.alium.dndapi.feature.creature;

import fr.alium.dndapi.feature.actionDnd.ActionDnDRepository;
import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.creature.entity.dto.CreatureDTO;
import fr.alium.dndapi.feature.language.LanguageRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creature")
public class CreatureController {
    CreatureRepository creatureRepository;
    LanguageRepository languageRepository;
    ActionDnDRepository actionDnDRepository;
    CreatureMapper creatureMapper;

    public CreatureController(
            CreatureRepository creatureRepository,
            LanguageRepository languageRepository,
            ActionDnDRepository actionDnDRepository,
            CreatureMapper creatureMapper
    ) {
        this.creatureRepository = creatureRepository;
        this.languageRepository = languageRepository;
        this.actionDnDRepository = actionDnDRepository;
        this.creatureMapper = creatureMapper;
    }

    @GetMapping
    public ResponseEntity<List<Creature>> readAll() {
        List<Creature> creatures = creatureRepository.findAll();
        return ResponseEntity.ok(creatures);
    }

    @GetMapping("{id}")
    public ResponseEntity<Creature> findById(@PathVariable Long id) {
        Creature creature = creatureRepository.findById(id).orElse(null);
        if (creature == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(creature);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreatureDTO creatureDTO, BindingResult result) {
        Creature creature = creatureMapper.toEntity(creatureDTO);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // creation language enfant
        if (!creature.getLanguages().isEmpty()) {
            languageRepository.saveAll(creature.getLanguages());
        }
        // creation ActionDnD enfants
        if (!creature.getActions().isEmpty()) {
            actionDnDRepository.saveAll(creature.getActions());
        }

        creatureRepository.save(creature);
        return ResponseEntity.ok("creature created");
    }

    @PatchMapping("{id}")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Creature creature, @PathVariable Long id, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        boolean exists = creatureRepository.existsById(id);
        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        creature.setId(id);
        creatureRepository.save(creature);
        return ResponseEntity.ok("creature updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean exists = creatureRepository.existsById(id);
        if (!exists) {
            return ResponseEntity.notFound().build();
        }
        creatureRepository.deleteById(id);
        return ResponseEntity.ok("creature deleted");
    }
}
